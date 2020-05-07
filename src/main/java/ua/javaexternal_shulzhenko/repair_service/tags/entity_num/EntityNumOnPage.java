package ua.javaexternal_shulzhenko.repair_service.tags.entity_num;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

public class EntityNumOnPage extends SimpleTagSupport {

    private String loop_count_num;
    private String page_num;
    private String entities_page_amount;

    @Override
    public void doTag() throws JspException, IOException {

        int counter = 0;
        int pageOffsetNum = 0;
        int entitiesPerPage = 0;

        if (loop_count_num.trim().length() != 0) {
            counter = Integer.parseInt(loop_count_num);
        }
        if (entities_page_amount.trim().length() != 0) {
            entitiesPerPage = Integer.parseInt(entities_page_amount);
        }
        if (page_num.trim().length() != 0) {
            pageOffsetNum = Integer.parseInt(page_num) - 1;
        }

        int entityNum = pageOffsetNum * entitiesPerPage + counter;

        getJspContext().getOut().print(entityNum);
    }

    public void setLoop_count_num(String loop_count_num) {
        this.loop_count_num = loop_count_num;
    }

    public void setPage_num(String page_num) {
        this.page_num = page_num;
    }

    public void setEntities_page_amount(String entities_page_amount) {
        this.entities_page_amount = entities_page_amount;
    }
}
