package loginapp;

public enum logintype {

        Admin, Student;

        private logintype() {

        }

        public String toString() {
            return name() ;

        }
        public static logintype fromvalue(String v){
                return valueOf(v);
        }
}
