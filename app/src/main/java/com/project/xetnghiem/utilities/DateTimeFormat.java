package com.project.xetnghiem.utilities;

public enum DateTimeFormat {
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    DATE_TIME_DB {
        public String toString() {
            return "yyyy-MM-dd HH:mm:ss";
        }
    }, /**
     * yyyy-MM-dd
     */
    DATE_TIME_DB_2 {
                public String toString() {
                    return "yyyy-MM-dd";
                }
            }, /**
     * yyyy-MM-dd'T'HH:mm:ss
     */
    DATE_TIME_DB_3 {
                public String toString() {
                    return "yyyy-MM-dd'T'HH:mm:ss";
                }
            },
    /**
     * h:mm a dd-MM-yyyy
     */
    DATE_TIME_APP {
        public String toString() {
            return "h:mm a dd-MM-yyyy";
        }
    }, /**
     * dd/MM/yyyy
     */
    DATE_FOTMAT {
                public String toString() {
                    return "dd-MM-yyyy";
                }
            },
    /**
     * dd-MM-yyyy
     */
    DATE_APP {
        public String toString() {
            return "dd-MM-yyyy";
        }
    },
    /**
     * dd/MM/yyyy
     */
    DATE_APP_2 {
        public String toString() {
            return "dd/MM/yyyy";
        }
    },
    /**
     * h:mm a
     */
    TIME_APP_2 {
        public String toString() {
            return "h:mm a";
        }
    }, /**
     * h:mm a
     */
    TIME_APP_1 {
        public String toString() {
            return "h:mm";
        }
    },
    /**
     * HH:mm:ss
     */
    TIME_DB_1 {
        public String toString() {
            return "HH:mm:ss";
        }
    }
}
