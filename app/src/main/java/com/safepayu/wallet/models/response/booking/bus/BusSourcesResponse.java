package com.safepayu.wallet.models.response.booking.bus;

import java.util.List;

public class BusSourcesResponse {


    /**
     * status : true
     * message : Bus Locations
     * data : [{"Id":100,"Name":"Hyderabad"},{"Id":101,"Name":"Delhi"},{"Id":102,"Name":"Avinashi"},{"Id":103,"Name":"Chennai"},{"Id":104,"Name":"Aurangabad"},{"Id":105,"Name":"Surat"},{"Id":106,"Name":"Ernakulam"},{"Id":107,"Name":"Chandigarh"},{"Id":108,"Name":"Manali"},{"Id":109,"Name":"Bangalore"},{"Id":110,"Name":"Ottapidaram"},{"Id":111,"Name":"Pathanapuram"},{"Id":112,"Name":"Krishnagiri"},{"Id":113,"Name":"Vijayawada"},{"Id":114,"Name":"Pune"},{"Id":115,"Name":"Sirsi"},{"Id":116,"Name":"Lokapur"},{"Id":117,"Name":"Humnabad"},{"Id":118,"Name":"Murudeshwara"},{"Id":119,"Name":"Hospet"},{"Id":120,"Name":"Koppal"},{"Id":121,"Name":"Kolhapur"},{"Id":122,"Name":"Mumbai"},{"Id":123,"Name":"Dharwad"},{"Id":124,"Name":"Sindagi"},{"Id":125,"Name":"Chitradurga"},{"Id":126,"Name":"Mangalore"},{"Id":127,"Name":"Vita"},{"Id":128,"Name":"Karkala"},{"Id":129,"Name":"Ujire"},{"Id":130,"Name":"Gulbarga"},{"Id":131,"Name":"Hubli"},{"Id":132,"Name":"Bellary"},{"Id":133,"Name":"Kundapura"},{"Id":134,"Name":"Raichur"},{"Id":135,"Name":"Sindhnur"},{"Id":136,"Name":"Belgaum"},{"Id":137,"Name":"Yaragatti"},{"Id":138,"Name":"Maragundi Lake"},{"Id":139,"Name":"Vapi"},{"Id":140,"Name":"Bagalkot"},{"Id":141,"Name":"Honavar"},{"Id":142,"Name":"Basavakalyan"},{"Id":143,"Name":"Bijapur"},{"Id":144,"Name":"Jewargi"},{"Id":145,"Name":"Kukke Subramanya"},{"Id":146,"Name":"Manipal"},{"Id":147,"Name":"Moodabidri"},{"Id":148,"Name":"Mulki"},{"Id":149,"Name":"Shirdi"},{"Id":150,"Name":"Kumta"},{"Id":151,"Name":"Vishakhapatnam"},{"Id":152,"Name":"Tirupathi"},{"Id":153,"Name":"Katapady"},{"Id":154,"Name":"Bardoli"},{"Id":155,"Name":"Udaipur"},{"Id":156,"Name":"Bhilwara"},{"Id":157,"Name":"Daisara"},{"Id":158,"Name":"Thrissur"},{"Id":159,"Name":"Calicut"},{"Id":160,"Name":"Mysore"},{"Id":161,"Name":"Alleppey"},{"Id":162,"Name":"Cochin"},{"Id":163,"Name":"Edappal"},{"Id":164,"Name":"Kochi"},{"Id":165,"Name":"Akola"},{"Id":166,"Name":"Karanja"},{"Id":167,"Name":"Darwha"},{"Id":168,"Name":"Arni"},{"Id":169,"Name":"Malegaon"},{"Id":170,"Name":"Mehkar"},{"Id":171,"Name":"Pusad"},{"Id":172,"Name":"Nagpur"},{"Id":173,"Name":"Kakinada"},{"Id":174,"Name":"Guntakal"},{"Id":175,"Name":"Ahmedabad"},{"Id":176,"Name":"Khedbrahma"},{"Id":177,"Name":"Guntur"},{"Id":178,"Name":"Patna"},{"Id":179,"Name":"Jamshedpur"},{"Id":180,"Name":"Gamharia"},{"Id":181,"Name":"Pondicherry"},{"Id":182,"Name":"Chidambaram"},{"Id":183,"Name":"Dindigul"},{"Id":184,"Name":"Theni"},{"Id":185,"Name":"Bathalagundu"},{"Id":186,"Name":"Thirumangalam"},{"Id":187,"Name":"Neyveli"},{"Id":188,"Name":"Velankanni"},{"Id":189,"Name":"Marthandam"},{"Id":190,"Name":"Kumbakonam"},{"Id":191,"Name":"Thiruvananthapuram"},{"Id":192,"Name":"Trichy"},{"Id":193,"Name":"Thiruchendur"},{"Id":194,"Name":"Tuticorin"},{"Id":195,"Name":"Valliyur Murugan temple"},{"Id":196,"Name":"Salem"},{"Id":197,"Name":"Palani"},{"Id":198,"Name":"Pollachi"},{"Id":199,"Name":"Coimbatore"},{"Id":200,"Name":"Palladam"},{"Id":201,"Name":"Cuddalore"},{"Id":202,"Name":"Nagercoil"},{"Id":203,"Name":"Thiruvarur"},{"Id":204,"Name":"Karaikal"},{"Id":205,"Name":"Sirkazhi"},{"Id":206,"Name":"Nagapattinam"},{"Id":207,"Name":"Tanjore"},{"Id":208,"Name":"Kanyakumari"},{"Id":209,"Name":"Palakkad"},{"Id":210,"Name":"Virudhachalam"},{"Id":211,"Name":"Kovilpatti"},{"Id":212,"Name":"Nagoor"},{"Id":213,"Name":"Ramnad"},{"Id":214,"Name":"Thirukadaiyur"},{"Id":215,"Name":"Panruti"},{"Id":216,"Name":"Angamaly"},{"Id":217,"Name":"Alwar"},{"Id":218,"Name":"Tirunelveli"},{"Id":219,"Name":"Mallipattinam"},{"Id":220,"Name":"Kattumavadi"},{"Id":221,"Name":"Manamelkudi"},{"Id":222,"Name":"Kottaippattinam"},{"Id":223,"Name":"Mimisal"},{"Id":224,"Name":"Thondi"},{"Id":225,"Name":"Devipattinam"},{"Id":226,"Name":"Malappuram"},{"Id":227,"Name":"Mannarkad"},{"Id":228,"Name":"Thisayanvilai"},{"Id":229,"Name":"Thirunallar"},{"Id":230,"Name":"Madurai"},{"Id":231,"Name":"Karur"},{"Id":232,"Name":"Karaikudi"},{"Id":233,"Name":"Devakottai"},{"Id":234,"Name":"Kangayam"},{"Id":235,"Name":"Vellakoil"}]
     */

    private boolean status;
    private String message;
    private List<DataBean> data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Id : 100
         * Name : Hyderabad
         */

        private int Id;
        private String Name;

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
