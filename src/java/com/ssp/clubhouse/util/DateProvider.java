package com.ssp.clubhouse.util;

import java.util.Date;

/**
 * User: stevenlothrop
 * Date: 11/14/12
 * Time: 11:14 PM
 */
public interface DateProvider {
    public Date getDate();

    public static final DateProvider SYSTEM = new DateProvider() {
        @Override
        public Date getDate() {
            return new Date();
        }
    };

}

