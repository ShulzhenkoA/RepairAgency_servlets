package ua.javaexternal_shulzhenko.repair_service.tags.blocks;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class BlocksParentTag extends SimpleTagSupport {
    private String servletPath;
    private boolean processed;

    @Override
    public void doTag() throws JspException, IOException {
        getJspBody().invoke(null);
    }

    public void setServletPath(String servletPath) {
        this.servletPath = servletPath;
    }

    public String getServletPath() {
        return servletPath;
    }

    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
}