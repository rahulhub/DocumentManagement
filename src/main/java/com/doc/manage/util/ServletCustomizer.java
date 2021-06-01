package com.doc.manage.util;

import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.server.MimeMappings;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.stereotype.Component;

@Component
class ServletCustomizer implements WebServerFactoryCustomizer<TomcatServletWebServerFactory> {

	@Override
	public void customize(TomcatServletWebServerFactory factory) {
		MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
		mappings.add("webm", "video/webm");
        mappings.add("mp4", "video/mp4");
        mappings.add("doc", "application/msword");
        mappings.add("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document");
        mappings.add("xls", "application/vnd.ms-excel");
        mappings.add("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        mappings.add("ppt", "application/vnd.ms-powerpoint");
        mappings.add("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation");
        mappings.add("csv", "text/csv");
        mappings.add("xls", "application/vnd.ms-excel");
        factory.setMimeMappings(mappings);
		
	}

}
