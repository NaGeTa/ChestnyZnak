package org.example;

import java.util.Date;
import java.util.List;

public class Document {
    Description description;
    String doc_id;
    String doc_status;
    String doc_type;
    boolean importRequest;
    String owner_inn;
    String participant_inn;
    String producer_inn;
    String production_date;
    String production_type;
    List<Product> products;
    String reg_date;
    String reg_number;

    @Override
    public String toString() {
        return "Document{" +
                "description=" + description +
                ", doc_id='" + doc_id + '\'' +
                ", doc_status='" + doc_status + '\'' +
                ", doc_type='" + doc_type + '\'' +
                ", importRequest=" + importRequest +
                ", owner_inn='" + owner_inn + '\'' +
                ", participant_inn='" + participant_inn + '\'' +
                ", producer_inn='" + producer_inn + '\'' +
                ", production_date='" + production_date + '\'' +
                ", production_type='" + production_type + '\'' +
                ", products=" + products +
                ", reg_date='" + reg_date + '\'' +
                ", reg_number='" + reg_number + '\'' +
                '}';
    }
}
