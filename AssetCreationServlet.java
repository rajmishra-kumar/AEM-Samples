import org.apache.commons.io.IOUtils;
import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.SlingHttpServletResponse;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
import org.apache.sling.api.servlets.HttpConstants;
import org.apache.sling.api.servlets.SlingAllMethodsServlet;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component(service = Servlet.class,
        property = {
                "sling.servlet.methods=" + HttpConstants.METHOD_GET,
                "sling.servlet.paths=" + "/bin/getvideo"
        })

public class AssetCreationServlet extends SlingAllMethodsServlet {

    private static final long serialVersionUID = 1L;
    private static final Logger LOGGER = LoggerFactory.getLogger(DAMAssetCreationServlet.class);

    @Reference
    private ResourceResolverFactory resourceResolverFactory;


    @Override
    protected void doGet(SlingHttpServletRequest request, SlingHttpServletResponse response) throws IOException {

        StringBuilder sb = new StringBuilder();
        ResourceResolver resolver = null;
        JSONObject json = null;
        PrintWriter out = response.getWriter();
        String assetPath=request.getParameter("assetpath");
        String assetPathJSON=assetPath+".json";
        String myJSON = IOUtils.toString(request.getReader());
        try {

            Map<String, Object> param = new HashMap<>();
            param.put(ResourceResolverFactory.SUBSERVICE, "testsystemuser");
            resolver = resourceResolverFactory.getServiceResourceResolver(param);
            String videoPath="/content/dam/ur-project/play.mp4";
            Resource resource=resolver.getResource(videoPath);


            json = new JSONObject(resource.getValueMap().toString());
        } catch (Exception e) {
            LOGGER.info(e.getMessage());
        }
        response.getWriter().println(json);
    }
}
