package atividade.mobile.tatiana.trabalhocontrolelivros.Enums;

public enum StatusBaseTypes {
    WISH(1), READING(2), STORED(3), LENT(4), OTHERS(5);

    private final int value;

    StatusBaseTypes(int value){
        this.value = value;
    }

    public int value(){return value;}
}
