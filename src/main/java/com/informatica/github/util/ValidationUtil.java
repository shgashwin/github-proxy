package com.informatica.github.util;

import java.util.Objects;

import com.informatica.github.controller.GithubProxyController;
import com.informatica.github.exception.InvalidParametersException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidationUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(GithubProxyController.class);

    private ValidationUtil() {
    }

    public static void validateQueryParam(final String q) {

        if (Objects.isNull(q)) {
            return;
        }

        // can add any validation in future.
        if (q.contains(" ")) {
            LOGGER.info("contains spaces");
            throw new InvalidParametersException("query parameters contains invalid string like space.");
        }
    }
}
