package org.example.week02.lombokpractice;

import lombok.Data;

public class DataPractice {


    public static void main(String[] args) {
        Person person = new Person(1);
        person.setName("Alice");
        person.setAge(30);
        person.setEmail("alice@example.com");

        System.out.println("Person: " + person);
    }

    @Data
    public static class Person {
        private final int id;
        private String name;
        private int age;
        private String email;

//        @Data equals to
//        @ToString
//        @EqualsAndHashCode
//        @Getter
//        @Setter
//        @RequiredArgsConstructor
//
//        Lombok generates:
//
//        public Person(int id) {
//            this.id = id;
//        }
//
//        public int getId() {
//            return this.id;
//        }
//
//        public String getName() {
//            return this.name;
//        }
//
//        public int getAge() {
//            return this.age;
//        }
//
//        public String getEmail() {
//            return this.email;
//        }
//
//        public void setName(String name) {
//            this.name = name;
//        }
//
//        public void setAge(int age) {
//            this.age = age;
//        }
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
//
//        public boolean equals(Object o) {
//            if (o == this) {
//                return true;
//            } else if (!(o instanceof Person)) {
//                return false;
//            } else {
//                Person other = (Person)o;
//                if (!other.canEqual(this)) {
//                    return false;
//                } else if (this.getId() != other.getId()) {
//                    return false;
//                } else if (this.getAge() != other.getAge()) {
//                    return false;
//                } else {
//                    Object this$name = this.getName();
//                    Object other$name = other.getName();
//                    if (this$name == null) {
//                        if (other$name != null) {
//                            return false;
//                        }
//                    } else if (!this$name.equals(other$name)) {
//                        return false;
//                    }
//
//                    Object this$email = this.getEmail();
//                    Object other$email = other.getEmail();
//                    if (this$email == null) {
//                        if (other$email != null) {
//                            return false;
//                        }
//                    } else if (!this$email.equals(other$email)) {
//                        return false;
//                    }
//
//                    return true;
//                }
//            }
//        }
//
//        protected boolean canEqual(Object other) {
//            return other instanceof Person;
//        }
//
//        public int hashCode() {
//            int PRIME = 59;
//            int result = 1;
//            result = result * 59 + this.getId();
//            result = result * 59 + this.getAge();
//            Object $name = this.getName();
//            result = result * 59 + ($name == null ? 43 : $name.hashCode());
//            Object $email = this.getEmail();
//            result = result * 59 + ($email == null ? 43 : $email.hashCode());
//            return result;
//        }

        public String toString() {
            int var10000 = this.getId();
            return "DataPractice.Person(id=" + var10000 + ", name=" + this.getName() + ", age=" + this.getAge() + ", email=" + this.getEmail() + ")";
        }

    }
}
