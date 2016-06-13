package com.garryiv.crawler.keywords;

import com.garryiv.crawler.model.InputSite;

/**
 * A service to resolve keywords from a site object
 */
public interface KeywordService {

    /**
     * Resolves a list of keywords associated with a site.
     *
     * @param site site to extract keywords
     * @return a comma delimited string or an empty string if there are no keywords associated with the site.
     */
    String resolveKeywords(InputSite site);

}
