package webserver.controllers;

import org.commonmark.html.HtmlRenderer;
import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;


@Controller
@RequestMapping("/api")
public class ApiDocsController {

    private final String url = "https://api.github.com/markdown/raw";
    private final String mdPath = "src/main/resources/api-docs.md";

    private String mdText = null;


    private Logger logger = LoggerFactory.getLogger(ApiDocsController.class);


    @RequestMapping(value = "/docs", method = RequestMethod.GET)
    public ResponseEntity<String> apiDocs() {

        logger.info("Returning docs");

        if (mdText == null) {
            initializeMdText();
        }

        if (mdText != null) {
            Parser parser = Parser.builder().build();
            Node document = parser.parse(mdText);
            HtmlRenderer renderer = HtmlRenderer.builder().build();
            String renderedHtml = renderer.render(document);

            return new ResponseEntity<>(renderedHtml, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void initializeMdText() {
        try {
            byte[] encodedMdText = Files.readAllBytes(Paths.get(mdPath));
            this.mdText = new String(encodedMdText, StandardCharsets.UTF_8);
        } catch (Exception e) {
            logger.warn("Unable to initialize mdText", e.getMessage(), e);
        }
    }
}
