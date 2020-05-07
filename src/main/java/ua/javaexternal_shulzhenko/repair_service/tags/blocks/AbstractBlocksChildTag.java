package ua.javaexternal_shulzhenko.repair_service.tags.blocks;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public abstract class AbstractBlocksChildTag extends SimpleTagSupport {
    public void doTag() throws JspException, IOException {
        JspTag tag = getParent();
        BlocksParentTag parentTag = null;
        if (tag instanceof BlocksParentTag) {
            parentTag = (BlocksParentTag) tag;
        }
        if (shouldExecute(parentTag)) {
            getJspBody().invoke(null);
        }
    }
    public abstract boolean shouldExecute(BlocksParentTag tag);
}