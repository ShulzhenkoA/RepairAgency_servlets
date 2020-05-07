package ua.javaexternal_shulzhenko.repair_service.tags.blocks;

import ua.javaexternal_shulzhenko.repair_service.services.proper_request.ProperRequestService;

public class SingleMainBlock extends AbstractBlocksChildTag {
    @Override
    public boolean shouldExecute(BlocksParentTag parentTag){
        String servletPath = parentTag.getServletPath();
        if (servletPath.equals("/login") ||
                servletPath.equals("/registration") ||
                    !ProperRequestService.isProperRequest(servletPath)) {
            parentTag.setProcessed(true);
            return true;
        }
        return false;
    }
}
