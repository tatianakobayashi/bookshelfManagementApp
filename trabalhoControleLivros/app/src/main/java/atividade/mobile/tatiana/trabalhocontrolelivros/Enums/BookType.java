package atividade.mobile.tatiana.trabalhocontrolelivros.Enums;

import java.io.Serializable;

public enum BookType implements Serializable {
    EBOOK("eBook", 0), PHYSICAL_COPY("Copia física", 1);

    private final String name;
    private final int value;
    BookType(String name, int value){
        this.name = name;
        this.value = value;
    }
    public int getValue(){return value;}
    public String typeName(){
        return name;
    }
}
