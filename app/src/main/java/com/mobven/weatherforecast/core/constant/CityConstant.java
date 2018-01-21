package com.mobven.weatherforecast.core.constant;


public enum CityConstant {

    Adana("01","Adana","325363"),
    Adiyaman("02","Adıyaman","325329"),
    Afyon("03","Afyonkarahisar","325303"),
    Agri("04","Ağrı","325163"),
    Amasya("05","Amasya","752015"),
    Ankara("06","Ankara","323786"),
    Antalya("07","Antalya","323776"),
    Artvin("08","Artvin","751816"),
    Aydın("09","Aydın","322819"),
    Balıkesir("10","Balıkesir","322164"),
    Bilecik("11","Bilecik","321122"),
    Bingöl("12","Bingöl","321079"),
    Bitlis("13","Bitlis","321022"),
    Bolu("14","Bolu","750510"),
    Burdur("15","Burdur","320390"),
    Bursa("16","Bursa","750268"),
    Çanakkale("17","Çanakkale","749778"),
    Çankırı("18","Çankırı","749747"),
    Çorum("19","Çorum","748877"),
    Denizli("20","Denizli","317109"),
    Diyarbakır("21","Diyarbakır","316540"),
    Edirne("22","Edirne","747711"),
    Elazığ("23","Elazığ","315807"),
    Erzincan("24","Erzincan","315372"),
    Erzurum("25","Erzurum","315367"),
    Eskişehir("26","Eskişehir","315201"),
    Gaziantep("27","Gaziantep","314829"),
    Giresun("28","Giresun","746878"),
    Gümüşhane("29","Gümüşhane","746423"),
    Hakkari("30","Hakkari","318137"),
    Hatay("31","Hatay","312394"),
    Isparta("32","Isparta","311071"),
    Mersin("33","Mersin","311728"),
    İstanbul("34","İstanbul","745042"),
    İzmir("35","İzmir","311046"),
    Kars("36","Kars","743942"),
    Kastamonu("37","Kastamonu","743881"),
    Kayseri("38","Kayseri","308464"),
    Kırklareli("39","Kırklareli","743165"),
    Kırşehir("40","Kırşehir","307513"),
    Kocaeli("41","Kocaeli","742865"),
    Konya("42","Konya","306571"),
    Kütahya("43","Kütahya","305267"),
    Malatya("44","Malatya","304919"),
    Manisa("45","Manisa","304825"),
    Karamanmaras("46","Kahramanmaraş","310859"),
    Mardin("47","Mardin","304797"),
    Muğla("48","Muğla","304183"),
    Muş("49","Muş","304041"),
    Nevşehir("50","Nevşehir","303830"),
    Niğde("51","Niğde","303826"),
    Ordu("52","Ordu","741099"),
    Rize("53","Rize","740481"),
    Sakarya("54","Sakarya","740352"),
    Samsun("55","Samsun","740263"),
    Siirt("56","Siirt","300821"),
    Sinop("57","Sinop","739598"),
    Sivas("58","Sivas","300617"),
    Tekirdağ("59","Tekirdağ","738926"),
    Tokat("60","Tokat","738742"),
    Trabzon("61","Trabzon","738647"),
    Tunceli("62","Tunceli","298845"),
    Şanlıurfa("63","Şanlıurfa","298332"),
    Uşak("64","Uşak","298298"),
    Van("65","Van","298117"),
    Yozgat("66","Yozgat","296562"),
    Zonguldak("67","Zonguldak","737021"),
    Aksaray("68","Aksaray","324496"),
    Bayburt("69","Bayburt","862471"),
    Karaman("70","Karaman","443187"),
    Kırıkkale("71","Kırıkkale","443188"),
    Batman("72","Batman","443186"),
    Şırnak("73","Şırnak","443189"),
    Bartın("74","Bartın","862467"),
    Ardahan("75","Ardahan","751952"),
    Iğdır("76","Iğdır","443184"),
    Yalova("77","Yalova","862469"),
    Karabük("78","Karabük","862468"),
    Kilis("79","Kilis","443213"),
    Osmaniye("80","Osmaniye","443183"),
    Düzce("81","Düzce","865521");

    String plateNum;
    String cityName;
    String cityCode;

    private CityConstant(String plateNum, String cityName,String cityCode) {
        this.plateNum = plateNum;
        this.cityName = cityName;
        this.cityCode = cityCode;
    }

    public String getPlateNum() {
        return plateNum;
    }

    public void setPlateNum(String plateNum) {
        this.plateNum = plateNum;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }
}
