package com.doc.manage.rest;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hslf.usermodel.HSLFSlide;
import org.apache.poi.hslf.usermodel.HSLFSlideShow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.UrlResource;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.doc.manage.DTO.EcomCatalogModel;
import com.doc.manage.DTO.EcomProductModel;
import com.doc.manage.entity.ApiResponse;
import com.doc.manage.entity.EcomProduct;
import com.doc.manage.entity.Upload;
import com.doc.manage.repository.ProductRepository;
import com.doc.manage.repository.UploadRepository;
import com.doc.manage.service.StorageService;
import com.doc.manage.service.UserService;
import com.doc.manage.util.EntityToModelUtil;
import com.doc.manage.util.ModelToEntityUtil;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfAction;
import com.lowagie.text.pdf.PdfDestination;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;

/**
 * Entry point into the web service.
 *
 * Using @RestController, so no need to autowire anything.
 */
@RestController
@RequestMapping(value = "/file")
public class UploadController {
    private final Log log = LogFactory.getLog(UploadController.class);
    private final UploadRepository repository;
    private final StorageService service;
    private final ProductRepository prodRepo;
    
    @Autowired
    private ServletContext servletContext;
    
    @Autowired
    private ModelToEntityUtil m2e;
    
    @Autowired
    private EntityToModelUtil e2m;

    /**
     * Required if I want the UploadRespository to be autowired.
     *
     * @param repository
     * @param service
     */
    public UploadController(UploadRepository repository, StorageService service,ProductRepository prodRepo){
        this.repository = repository;
        this.service = service;
        this.prodRepo = prodRepo;
    }


    /**
     * Method for getting a list of all files
     *
     * @return Collection containing record of all uploads
     */
    @RequestMapping(value = "/all", method = GET)
    public Resources<EcomProduct> findAll(){
        log.info("findAll");
        Collection<EcomProduct> out = prodRepo.findAll();
        return new Resources<>(out);
    }

    /**
     * Method for uploading a file
     *
     * @param owner
     * @param description
     * @param file
     * @return Upload object
     */
    @RequestMapping(value = "/upload/{owner}/{description}", method = POST)
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public Resource<EcomProductModel> upload(@PathVariable("owner") String owner,
                         @PathVariable("description") String description,
                         @RequestParam MultipartFile file, @RequestPart("path") String path,
                         @RequestPart("catId") String catId){

        log.info("uploading file owned by " + owner +" description: " + description + " Path " +path );

        // log.info("uploading file owned by " + owner +" description: " + description);
        // log.info("uploading " + file.getOriginalFilename() + " to uploads directory");
        String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));

        service.store(path, file);
        
        EcomProductModel prodmodel = new EcomProductModel(new Date(),owner,path+file.getOriginalFilename(),description,file.getOriginalFilename(),file.getSize(),
        		new EcomCatalogModel(Long.valueOf(catId)),extension);
        
        EcomProduct forSave = m2e.convertProductTypeModelToEntity(prodmodel);

        EcomProduct out = prodRepo.save(forSave);

        // log.info("EXISTS: " + repository.findByFilename(file.getOriginalFilename()).toString());

        return new Resource<>(e2m.convertProductTypeToModel(out));
    }

    /**
     * Method for searching for file information by fileid
     *
     * @param id
     * @return Upload object, else HttpStatus.NotFound
     */
    @RequestMapping(value = "/details/{id}", method = GET)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
    public Resource<?> findByID(@PathVariable(value = "id") long id){
        log.info("findByID " + id);
        Upload out = repository.findByFileId(id).orElse(null);
        if (out == null) return new Resource<>(HttpStatus.NOT_FOUND);
        return new Resource<>(out);
    }

    /**
     * Method to get file details by filename
     *
     * @param filename
     * @return Upload object, else HttpStatus.NotFound
     */
    @RequestMapping(value = "/details/filename/{filename}/", method = GET)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
    public Resource<?> findByFilename(@PathVariable(value = "filename") String filename){
        log.info("findByFilename: " + filename);
        Upload out = repository.findByFilename(filename).orElse(null);
        if(out == null) return new Resource<>(HttpStatus.NOT_FOUND);
        return new Resource<>(out);
    }

    /**
     * Method for getting all files uploaded in the last hour
     *
     * @return Collection containing Upload objects
     */
    @RequestMapping(value = "/recent", method = GET)
    public Resources<Upload> uploadedLastHour(){
        log.info("uploadedLastHour now: " + LocalDateTime.now() + " hour ago: " + LocalDateTime.now().minusHours(1));
        return new Resources<>(repository.findByDateUploadedAfter(Timestamp.valueOf(LocalDateTime.now().minusHours(1))));
    }
    
    @RequestMapping(value = "/viewstream/{id}", method = GET)
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<org.springframework.core.io.Resource> viewStreamByID(@PathVariable(value = "id") long id) throws IOException{
    	log.info("streamByID " + id);
        EcomProduct out = prodRepo.findByProductId(id);

        if(out == null) return (ResponseEntity<org.springframework.core.io.Resource>) ResponseEntity.notFound();
        
        String mineType = servletContext.getMimeType(out.getName()+out.getExtension());
        
        org.springframework.core.io.Resource file = service.loadAsResource(out);
        InputStream doc = file.getInputStream();
        PdfOptions options = PdfOptions.create();
        String[] str = out.getName().split("[.]");
        
        File newfile = null;
    	if(str.length == 2) {
    	newfile = File.createTempFile(str[0], ".pdf");
    	}else {
    		newfile = File.createTempFile(out.getName(), ".pdf");
    	}
        if(out.getExtension().equalsIgnoreCase(".docx")) {
        	XWPFDocument document = new XWPFDocument(doc);
        	OutputStream outFile = new FileOutputStream(newfile);
        	PdfConverter.getInstance().convert(document, outFile, options);
        	file = new UrlResource(newfile.toURI());
        	 mineType = servletContext.getMimeType(out.getName()+".pdf");
        }else if (out.getExtension().equalsIgnoreCase(".doc")) {
        	POIFSFileSystem fs = null;  
            Document document = new Document();
            fs = new POIFSFileSystem(doc);  

            HWPFDocument hwpfDoc = new HWPFDocument(fs);  
            WordExtractor we = new WordExtractor(hwpfDoc);  
            OutputStream outFile = new FileOutputStream(newfile); 

            try {
				PdfWriter writer = PdfWriter.getInstance(document, outFile);
				 Range range = hwpfDoc.getRange();
	             document.open();  
	             writer.setPageEmpty(true);  
	             document.newPage();  
	             writer.setPageEmpty(true); 
	             String[] paragraphs = we.getParagraphText();  
	             for (int i = 0; i < paragraphs.length; i++) {  
	            	 org.apache.poi.hwpf.usermodel.Paragraph pr = range.getParagraph(i);
	                 // CharacterRun run = pr.getCharacterRun(i);
	                 // run.setBold(true);
	                 // run.setCapitalized(true);
	                 // run.setItalic(true);
	                  paragraphs[i] = paragraphs[i].replaceAll("\\cM?\r?\n", "");  
//	              System.out.println("Length:" + paragraphs[i].length());  
//	              System.out.println("Paragraph" + i + ": " + paragraphs[i].toString());  

	              // add the paragraph to the document  
	              document.add(new Paragraph(paragraphs[i]));  
	             }

	             
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  finally {  
                // close the document  
				document.close();  
            }  
            
            file = new UrlResource(newfile.toURI());
            mineType = servletContext.getMimeType(out.getName()+".pdf");

        }else if(out.getExtension().equalsIgnoreCase(".xlsx") || out.getExtension().equalsIgnoreCase(".xls")) {
        	createXLSXtoPDF(file.getInputStream(),newfile,out.getExtension());
        	file = new UrlResource(newfile.toURI());
        	 mineType = servletContext.getMimeType(out.getName()+".pdf");
        }else if(out.getExtension().equalsIgnoreCase(".pptx") || out.getExtension().equalsIgnoreCase(".ppt")) {
        	convertPPTToPDF(file.getInputStream(),newfile,out.getExtension());
        	file = new UrlResource(newfile.toURI());
        	 mineType = servletContext.getMimeType(out.getName()+".pdf");
        }
        
       
        MediaType mediaType = null;
        try {
            mediaType = MediaType.parseMediaType(mineType);
            
        } catch (Exception e) {
        	mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        
        return ResponseEntity.
                ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+out.getName()+"\"").
                contentType(mediaType).
                contentLength(file.contentLength()).
                body(file);
    }
        
    public void convertPPTToPDF(InputStream inputStream, File newfile, String fileType) {
//        FileInputStream inputStream = new FileInputStream(sourcepath);
        double zoom = 2;
        AffineTransform at = new AffineTransform();
        at.setToScale(zoom, zoom);
        Document pdfDocument = new Document();
        PdfWriter pdfWriter=null;
		try {
			pdfWriter = PdfWriter.getInstance(pdfDocument, new FileOutputStream(newfile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        PdfPTable table = new PdfPTable(1);
        pdfWriter.open();
        pdfDocument.open();
        Dimension pgsize = null;
        Image slideImage = null;
        BufferedImage img = null;
        if (fileType.equalsIgnoreCase(".ppt")) {
            HSLFSlideShow ppt=null;
			try {
				ppt = new HSLFSlideShow(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            pgsize = ppt.getPageSize();
            List<HSLFSlide> slide = ppt.getSlides();
            pdfDocument.setPageSize(new Rectangle((float) pgsize.getWidth(), (float) pgsize.getHeight()));
            pdfWriter.open();
            pdfDocument.open();
            for (int i = 0; i < slide.size(); i++) {
                img = new BufferedImage((int) Math.ceil(pgsize.width * zoom), (int) Math.ceil(pgsize.height * zoom), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setTransform(at);

                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                slide.get(i).draw(graphics);
                graphics.getPaint();
                try {
					slideImage = Image.getInstance(img, null);
				} catch (BadElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                table.addCell(new PdfPCell(slideImage, true));
            }
        }
        if (fileType.equalsIgnoreCase(".pptx")) {
            XMLSlideShow ppt=null;
			try {
				ppt = new XMLSlideShow(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            pgsize = ppt.getPageSize();
            List<XSLFSlide> slide = ppt.getSlides();
            pdfDocument.setPageSize(new Rectangle((float) pgsize.getWidth(), (float) pgsize.getHeight()));
            pdfWriter.open();
            pdfDocument.open();
            for (int i = 0; i < slide.size(); i++) {
                img = new BufferedImage((int) Math.ceil(pgsize.width * zoom), (int) Math.ceil(pgsize.height * zoom), BufferedImage.TYPE_INT_RGB);
                Graphics2D graphics = img.createGraphics();
                graphics.setTransform(at);

                graphics.setPaint(Color.white);
                graphics.fill(new Rectangle2D.Float(0, 0, pgsize.width, pgsize.height));
                slide.get(i).draw(graphics);
                graphics.getPaint();
                try {
					slideImage = Image.getInstance(img, null);
				} catch (BadElementException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
                table.addCell(new PdfPCell(slideImage, true));
            }
        }
        try {
			pdfDocument.add(table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        pdfDocument.close();
        pdfWriter.close();
        System.out.println("Powerpoint file converted to PDF successfully");
    }

    private void createXLSXtoPDF(InputStream filecontent, File newfile, String fileType) {
//    	FileInputStream filecontent = new FileInputStream(new File(sourcepath));
        FileOutputStream out=null;
		try {
			out = new FileOutputStream(newfile);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        HSSFWorkbook my_xls_workbook = null;
        HSSFSheet my_worksheet = null;
        XSSFWorkbook my_xlsx_workbook = null;
        XSSFSheet my_worksheet_xlsx = null;
        Document document = new Document(PageSize.ARCH_E, 0, 0, 0, 0);
        PdfWriter writer=null;
		try {
			writer = PdfWriter.getInstance(document, out);
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        document.open();
        PdfDestination magnify = null;
        float magnifyOpt = (float) 70.0;
        magnify = new PdfDestination(PdfDestination.XYZ, -1, -1, magnifyOpt / 100);
        int pageNumberToOpenTo = 1;
        PdfAction zoom = PdfAction.gotoLocalPage(pageNumberToOpenTo, magnify, writer);
        writer.setOpenAction(zoom);
        document.addDocListener(writer);

        Iterator<Row> rowIterator = null;
        int maxColumn = 0;
        if (fileType.equals(".xls")) {
            try {
                my_xls_workbook = new HSSFWorkbook(filecontent);
                my_worksheet = my_xls_workbook.getSheetAt(0);
                rowIterator = my_worksheet.iterator();
                maxColumn = my_worksheet.getRow(0).getLastCellNum();
            } catch (IOException ex) {
            	log.error(ex.getMessage());
            }
        }

        if (fileType.equals(".xlsx")) {
            try {
                my_xlsx_workbook = new XSSFWorkbook(filecontent);
                my_worksheet_xlsx = my_xlsx_workbook.getSheetAt(0);
                rowIterator = my_worksheet_xlsx.iterator();
                maxColumn = my_worksheet_xlsx.getRow(0).getLastCellNum();
            } catch (IOException ex) {
            	log.error(ex.getMessage());
            }
        }
        PdfPTable my_table = new PdfPTable(maxColumn);
        my_table.setHorizontalAlignment(Element.ALIGN_CENTER);
        my_table.setWidthPercentage(100);
        my_table.setSpacingBefore(0f);
        my_table.setSpacingAfter(0f);
        PdfPCell table_cell;
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next(); //Fetch CELL
                switch (cell.getCellType()) { //Identify CELL type
                    case STRING:
                        //Push the data from Excel to PDF Cell
                        table_cell = new PdfPCell(new Phrase(cell.getStringCellValue()));
//                        if (row.getRowNum() == 0) {
//                            table_cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//                            table_cell.setBorderColor(BaseColor.BLACK);
//                        }
                        my_table.addCell(table_cell);
                        break;
                }
            }
        }
        try {
			document.add(my_table);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        document.close();
        System.out.println("Excel file converted to PDF successfully");
	}


	/**
     * Method for getting file stream by fileid
     *
     * @param id
     * @return InputStream if file exists
     * @throws IOException 
     */
    @RequestMapping(value = "/stream/{id}", method = GET)
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ResponseEntity<org.springframework.core.io.Resource> streamByID(@PathVariable(value = "id") long id) throws IOException{
        log.info("streamByID " + id);
        EcomProduct out = prodRepo.findByProductId(id);

        if(out == null) return (ResponseEntity<org.springframework.core.io.Resource>) ResponseEntity.notFound();
        
       
        String mineType = servletContext.getMimeType(out.getName()+out.getExtension());
        
        org.springframework.core.io.Resource file = service.loadAsResource(out);
       
        MediaType mediaType = null;
        try {
            mediaType = MediaType.parseMediaType(mineType);
            
        } catch (Exception e) {
        	mediaType = MediaType.APPLICATION_OCTET_STREAM;
        }
        

        return ResponseEntity.
                ok().
                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+out.getName()+"\"").
                contentType(mediaType).
                contentLength(file.contentLength()).
                body(file);
    }
    
//    private boolean hasViewRole() {
//  	  Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//  	  boolean hasRole = false;
//  	  if (principal instanceof User) {
//  		  String userRole = ((User) principal).getAuthorities().iterator().next().getAuthority();
//  		  if(userRole.equalsIgnoreCase("ROLE_USER"))
//  			  hasRole=true;
//  	  }
//  	  return hasRole;
//  	}  

    /**
     * Method for getting file stream by fileid
     *
     * @param filename
     * @return InputStream if file exists
     */
//    @RequestMapping(value = "/stream/filename/{fileName}", method = POST, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
//    public ResponseEntity<org.springframework.core.io.Resource> streamByFilename(@PathVariable(value = "fileName") String filename){
//        log.info("streamByFilename " + filename);
//        Upload out = repository.findByFilename(filename).get();
//
//        if(out == null) return (ResponseEntity<org.springframework.core.io.Resource>) ResponseEntity.notFound();
//
//        org.springframework.core.io.Resource file = service.loadAsResource(filename);
//
//        return ResponseEntity.
//                ok().
//                header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+filename+"\"").
//                body(file);
//    }

    /**
     * Method to find all files uploaded by a specific owner.
     *
     * @param owner
     * @return Resources containing all uploads for given owner
     */
    @RequestMapping(value = "/owner/{owner}", method = GET)
    public Resources<Upload> getByOwner(@PathVariable("owner") String owner){
        log.info("getByOwner: " + owner);
        return new Resources<>(repository.findByOwner(owner));
    }
    
    @Autowired
    private UserService userService;
    
    @RequestMapping(value = "/{userName}", method = GET)
    @PreAuthorize("hasAnyRole('USER', 'ADMIN','SUPER_ADMIN')")
    public ApiResponse<String> getAccess(@PathVariable(value="userName") String userName) {
        UserDetails userDetails = userService.loadUserByUsername(userName);
        List<String> listOfRoles = new ArrayList<String>();
        userDetails.getAuthorities().forEach(auth -> {
        	listOfRoles.add(auth.getAuthority());
        });
        return new ApiResponse<String>(HttpStatus.OK.value(), "User fetched successfully.", listOfRoles.toString());
    }

    /**
     * This bean is necessary for the entire program to run.
     * It creates the uploads directory where the files will be stored
     * It also deletes any existing files in the directory on start. SO BE CAREFUL
     *
     * @param service
     * @return
     */
    @Bean
    CommandLineRunner init(StorageService service){
        return (args -> {
//            service.deleteAll();
            service.init();
        });
    }
    
    
    
    @RequestMapping(value = "/uploadUserPic/{owner}", method = POST)
    @PreAuthorize("hasRole('ADMIN') or hasRole('SUPER_ADMIN')")
    public ApiResponse<String> uploadUserPic(@PathVariable("owner") String owner,
                         @RequestParam MultipartFile file, @RequestPart("path") String path){

        log.info("uploading pic owned by " + owner + " Path " +path );

        // log.info("uploading file owned by " + owner +" description: " + description);
        // log.info("uploading " + file.getOriginalFilename() + " to uploads directory");
       // String extension = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));

        service.storeResource(path, file);
        
      
        // log.info("EXISTS: " + repository.findByFilename(file.getOriginalFilename()).toString());

        return new ApiResponse<>(HttpStatus.OK.value(), "User fetched successfully.","resource/"+path + file.getOriginalFilename());
    }
}

