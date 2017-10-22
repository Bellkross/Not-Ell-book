package ua.bellkross.bellnotebook.model;

public class Contact {

    private int positionInList;
    private int positionInDB;
    private String photoUri;
    private String name;
    private String surname;
    private String number;
    private String birthday;
    private String additionalInformation;

    public Contact() {
        this.positionInList = Constants.NEW_ELEMENT;
        this.positionInDB = Constants.NEW_ELEMENT;
        this.photoUri = Constants.EMPTY_URI;
        this.name = "";
        this.surname = "";
        this.number = "";
        this.birthday = "";
        this.additionalInformation = "";
    }

    public Contact(String name) {
        this.positionInList = Constants.NEW_ELEMENT;
        this.positionInDB = Constants.NEW_ELEMENT;
        this.photoUri = Constants.EMPTY_URI;
        this.name = name;
        this.surname = surname;
        this.number = "";
        this.birthday = "";
        this.additionalInformation = "";
    }

    public Contact(int positionInList, int positionInDB, String photoUri, String name, String surname,
                   String number, String birthday, String additionalInformation) {
        this.positionInList = positionInList;
        this.positionInDB = positionInDB;
        this.photoUri = photoUri;
        this.name = name;
        this.surname = surname;
        this.number = number;
        this.birthday = birthday;
        this.additionalInformation = additionalInformation;
    }

    public int getPositionInList() {
        return positionInList;
    }

    public void setPositionInList(int positionInList) {
        this.positionInList = positionInList;
    }

    public int getPositionInDB() {
        return positionInDB;
    }

    public void setPositionInDB(int positionInDB) {
        this.positionInDB = positionInDB;
    }

    public String getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(String photoUri) {
        this.photoUri = photoUri;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "positionInList=" + positionInList +
                ", positionInDB=" + positionInDB +
                ", photoUri='" + photoUri + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", number='" + number + '\'' +
                ", birthday='" + birthday + '\'' +
                ", additionalInformation='" + additionalInformation + '\'' +
                '}';
    }
}
