package com.example.dguamazon;

public class SubwayItem {

        private String name;
        private String subName;
        private int code;

        public String getName() {
            return name;
        }

        public String getSubName() {
            return subName;
        }

        public SubwayItem(String name, String subName, int code) {
            this.name = name;
            this.subName = subName;
            this.code = code;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
}