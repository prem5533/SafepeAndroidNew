package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class ProductsDetailsResponse {

    /**
     * status : true
     * message : Product Details
     * venues : {"id":2,"merchant_id":2,"venue_id":"201911011148462","venue_name":"LillyWhites","venue_images":["1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg","20191121064555.lillywhites.jpg","20191121064555.lillywhites-sports-shopdepartment-store-londons-west-end-london-engtand-DA53PJ.jpg"],"address_1":"Oaklands Road, Wolverhampton WV3 0DS, England, UK","address_2":null,"city_name":"Wolverhampton","country":"United Kingdom","post_code":"WV3 0DS","phone_number":"5468496484","venue_email":"whiteslilly@dmailpro.net","venue_website":"https://www.lillywhites.com/","home_delivery_mov":"0.30","start_days":1,"end_days":2,"collection_time":20,"preparation_time":30,"free_delivery":"0.30","delivery_charge":"0.30","delivery":1,"collection":1,"opening_time":"06:15","closing_time":"20:30"}
     * products : {"id":20,"merchant_id":2,"venue_id":"201911011148462","product_name":"OnePlus 138.8 cm (55 inches)","product_description":"This TV doesn't come with table-top stand. It comes with a wall mount inside the box. Table-top stand has to be purchased separately if needed.\nResolution: 4K Ultra HD (3840x2160) | Motion Rate 480 Hertz\nConnectivity: 4 HDMI ports (HDMI 2 supports ARC) to connect set top box, Blu Ray players, gaming console | 3 USB port (USB 3.0*1, USB 2.0*1, USB Type C*1) to connect hard drives and other USB devices\nSound : 50 Watts Output | Alluring sound | Dolby Atmos | Full range 2 Speaker | 2 Sub-woofer","measurement_unit_id":2,"category_id":["5"],"related_product_ids":[],"brand_id":9,"tax_id":"0","supplier_id":2,"product_tags":"TV","images":["uploaded/products/3750215730270990.jpeg","uploaded/products/1679415730270990.jpeg","",""],"modifier_list":[],"modifier_id":20,"modifier_name":null,"buy_price":"120.00","selling_price":"150.00","avl_quantity":199,"modifier_images":[],"productsRating":0}
     * productOfers : []
     * relatedproduct : []
     * loyalitypoints : {"loyalty_points_value":0.25,"loyalty_points":20,"total_loyalty_points_value":5}
     * modifiers : [{"id":20,"product_id":20,"modifier_name":null,"sku":"8898989","weight":"0","buy_price":"120.00","sell_price":"150.00","barcode":"9484489","quantity":199,"moq":1,"modifier_images":null,"product_barcode_image":"uploaded/barcodeImage/8559815730269910.png","product_qrcode_image":"uploaded/qrcodeImage/5884115730269910.png","created_at":null,"updated_at":null},{"id":23,"product_id":20,"modifier_name":null,"sku":"8898989","weight":"0","buy_price":"120.00","sell_price":"150.00","barcode":"94844898","quantity":10,"moq":1,"modifier_images":null,"product_barcode_image":"uploaded/barcodeImage/8163915730349580.png","product_qrcode_image":"uploaded/qrcodeImage/3482915730349580.png","created_at":null,"updated_at":null},{"id":116,"product_id":20,"modifier_name":"15","sku":null,"weight":"0","buy_price":"0.00","sell_price":"0.00","barcode":null,"quantity":200,"moq":0,"modifier_images":null,"product_barcode_image":null,"product_qrcode_image":null,"created_at":null,"updated_at":null}]
     */

    private boolean status;
    private String message;
    private VenuesBean venues;
    private ProductsBean products;
    private LoyalitypointsBean loyalitypoints;
    private List<?> productOfers;
    private List<?> relatedproduct;
    private List<ModifiersBean> modifiers;

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

    public VenuesBean getVenues() {
        return venues;
    }

    public void setVenues(VenuesBean venues) {
        this.venues = venues;
    }

    public ProductsBean getProducts() {
        return products;
    }

    public void setProducts(ProductsBean products) {
        this.products = products;
    }

    public LoyalitypointsBean getLoyalitypoints() {
        return loyalitypoints;
    }

    public void setLoyalitypoints(LoyalitypointsBean loyalitypoints) {
        this.loyalitypoints = loyalitypoints;
    }

    public List<?> getProductOfers() {
        return productOfers;
    }

    public void setProductOfers(List<?> productOfers) {
        this.productOfers = productOfers;
    }

    public List<?> getRelatedproduct() {
        return relatedproduct;
    }

    public void setRelatedproduct(List<?> relatedproduct) {
        this.relatedproduct = relatedproduct;
    }

    public List<ModifiersBean> getModifiers() {
        return modifiers;
    }

    public void setModifiers(List<ModifiersBean> modifiers) {
        this.modifiers = modifiers;
    }

    public static class VenuesBean {
        /**
         * id : 2
         * merchant_id : 2
         * venue_id : 201911011148462
         * venue_name : LillyWhites
         * venue_images : ["1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg","20191121064555.lillywhites.jpg","20191121064555.lillywhites-sports-shopdepartment-store-londons-west-end-london-engtand-DA53PJ.jpg"]
         * address_1 : Oaklands Road, Wolverhampton WV3 0DS, England, UK
         * address_2 : null
         * city_name : Wolverhampton
         * country : United Kingdom
         * post_code : WV3 0DS
         * phone_number : 5468496484
         * venue_email : whiteslilly@dmailpro.net
         * venue_website : https://www.lillywhites.com/
         * home_delivery_mov : 0.30
         * start_days : 1
         * end_days : 2
         * collection_time : 20
         * preparation_time : 30
         * free_delivery : 0.30
         * delivery_charge : 0.30
         * delivery : 1
         * collection : 1
         * opening_time : 06:15
         * closing_time : 20:30
         */

        private int id;
        private int merchant_id;
        private String venue_id;
        private String venue_name;
        private String address_1;
        private Object address_2;
        private String city_name;
        private String country;
        private String post_code;
        private String phone_number;
        private String venue_email;
        private String venue_website;
        private String home_delivery_mov;
        private int start_days;
        private int end_days;
        private int collection_time;
        private int preparation_time;
        private String free_delivery;
        private String delivery_charge;
        private int delivery;
        private int collection;
        private String opening_time;
        private String closing_time;
        private List<String> venue_images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getVenue_id() {
            return venue_id;
        }

        public void setVenue_id(String venue_id) {
            this.venue_id = venue_id;
        }

        public String getVenue_name() {
            return venue_name;
        }

        public void setVenue_name(String venue_name) {
            this.venue_name = venue_name;
        }

        public String getAddress_1() {
            return address_1;
        }

        public void setAddress_1(String address_1) {
            this.address_1 = address_1;
        }

        public Object getAddress_2() {
            return address_2;
        }

        public void setAddress_2(Object address_2) {
            this.address_2 = address_2;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getPost_code() {
            return post_code;
        }

        public void setPost_code(String post_code) {
            this.post_code = post_code;
        }

        public String getPhone_number() {
            return phone_number;
        }

        public void setPhone_number(String phone_number) {
            this.phone_number = phone_number;
        }

        public String getVenue_email() {
            return venue_email;
        }

        public void setVenue_email(String venue_email) {
            this.venue_email = venue_email;
        }

        public String getVenue_website() {
            return venue_website;
        }

        public void setVenue_website(String venue_website) {
            this.venue_website = venue_website;
        }

        public String getHome_delivery_mov() {
            return home_delivery_mov;
        }

        public void setHome_delivery_mov(String home_delivery_mov) {
            this.home_delivery_mov = home_delivery_mov;
        }

        public int getStart_days() {
            return start_days;
        }

        public void setStart_days(int start_days) {
            this.start_days = start_days;
        }

        public int getEnd_days() {
            return end_days;
        }

        public void setEnd_days(int end_days) {
            this.end_days = end_days;
        }

        public int getCollection_time() {
            return collection_time;
        }

        public void setCollection_time(int collection_time) {
            this.collection_time = collection_time;
        }

        public int getPreparation_time() {
            return preparation_time;
        }

        public void setPreparation_time(int preparation_time) {
            this.preparation_time = preparation_time;
        }

        public String getFree_delivery() {
            return free_delivery;
        }

        public void setFree_delivery(String free_delivery) {
            this.free_delivery = free_delivery;
        }

        public String getDelivery_charge() {
            return delivery_charge;
        }

        public void setDelivery_charge(String delivery_charge) {
            this.delivery_charge = delivery_charge;
        }

        public int getDelivery() {
            return delivery;
        }

        public void setDelivery(int delivery) {
            this.delivery = delivery;
        }

        public int getCollection() {
            return collection;
        }

        public void setCollection(int collection) {
            this.collection = collection;
        }

        public String getOpening_time() {
            return opening_time;
        }

        public void setOpening_time(String opening_time) {
            this.opening_time = opening_time;
        }

        public String getClosing_time() {
            return closing_time;
        }

        public void setClosing_time(String closing_time) {
            this.closing_time = closing_time;
        }

        public List<String> getVenue_images() {
            return venue_images;
        }

        public void setVenue_images(List<String> venue_images) {
            this.venue_images = venue_images;
        }
    }

    public static class ProductsBean {
        /**
         * id : 20
         * merchant_id : 2
         * venue_id : 201911011148462
         * product_name : OnePlus 138.8 cm (55 inches)
         * product_description : This TV doesn't come with table-top stand. It comes with a wall mount inside the box. Table-top stand has to be purchased separately if needed.
         Resolution: 4K Ultra HD (3840x2160) | Motion Rate 480 Hertz
         Connectivity: 4 HDMI ports (HDMI 2 supports ARC) to connect set top box, Blu Ray players, gaming console | 3 USB port (USB 3.0*1, USB 2.0*1, USB Type C*1) to connect hard drives and other USB devices
         Sound : 50 Watts Output | Alluring sound | Dolby Atmos | Full range 2 Speaker | 2 Sub-woofer
         * measurement_unit_id : 2
         * category_id : ["5"]
         * related_product_ids : []
         * brand_id : 9
         * tax_id : 0
         * supplier_id : 2
         * product_tags : TV
         * images : ["uploaded/products/3750215730270990.jpeg","uploaded/products/1679415730270990.jpeg","",""]
         * modifier_list : []
         * modifier_id : 20
         * modifier_name : null
         * buy_price : 120.00
         * selling_price : 150.00
         * avl_quantity : 199
         * modifier_images : []
         * productsRating : 0
         */

        private int id;
        private int merchant_id;
        private String venue_id;
        private String product_name;
        private String product_description;
        private int measurement_unit_id;
        private int brand_id;
        private String tax_id;
        private int supplier_id;
        private String product_tags;
        private int modifier_id;
        private Object modifier_name;
        private String buy_price;
        private String selling_price;
        private int avl_quantity;
        private int productsRating;
        private List<String> category_id;
        private List<?> related_product_ids;
        private List<String> images;
        private List<?> modifier_list;
        private List<?> modifier_images;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public String getVenue_id() {
            return venue_id;
        }

        public void setVenue_id(String venue_id) {
            this.venue_id = venue_id;
        }

        public String getProduct_name() {
            return product_name;
        }

        public void setProduct_name(String product_name) {
            this.product_name = product_name;
        }

        public String getProduct_description() {
            return product_description;
        }

        public void setProduct_description(String product_description) {
            this.product_description = product_description;
        }

        public int getMeasurement_unit_id() {
            return measurement_unit_id;
        }

        public void setMeasurement_unit_id(int measurement_unit_id) {
            this.measurement_unit_id = measurement_unit_id;
        }

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }

        public String getTax_id() {
            return tax_id;
        }

        public void setTax_id(String tax_id) {
            this.tax_id = tax_id;
        }

        public int getSupplier_id() {
            return supplier_id;
        }

        public void setSupplier_id(int supplier_id) {
            this.supplier_id = supplier_id;
        }

        public String getProduct_tags() {
            return product_tags;
        }

        public void setProduct_tags(String product_tags) {
            this.product_tags = product_tags;
        }

        public int getModifier_id() {
            return modifier_id;
        }

        public void setModifier_id(int modifier_id) {
            this.modifier_id = modifier_id;
        }

        public Object getModifier_name() {
            return modifier_name;
        }

        public void setModifier_name(Object modifier_name) {
            this.modifier_name = modifier_name;
        }

        public String getBuy_price() {
            return buy_price;
        }

        public void setBuy_price(String buy_price) {
            this.buy_price = buy_price;
        }

        public String getSelling_price() {
            return selling_price;
        }

        public void setSelling_price(String selling_price) {
            this.selling_price = selling_price;
        }

        public int getAvl_quantity() {
            return avl_quantity;
        }

        public void setAvl_quantity(int avl_quantity) {
            this.avl_quantity = avl_quantity;
        }

        public int getProductsRating() {
            return productsRating;
        }

        public void setProductsRating(int productsRating) {
            this.productsRating = productsRating;
        }

        public List<String> getCategory_id() {
            return category_id;
        }

        public void setCategory_id(List<String> category_id) {
            this.category_id = category_id;
        }

        public List<?> getRelated_product_ids() {
            return related_product_ids;
        }

        public void setRelated_product_ids(List<?> related_product_ids) {
            this.related_product_ids = related_product_ids;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<?> getModifier_list() {
            return modifier_list;
        }

        public void setModifier_list(List<?> modifier_list) {
            this.modifier_list = modifier_list;
        }

        public List<?> getModifier_images() {
            return modifier_images;
        }

        public void setModifier_images(List<?> modifier_images) {
            this.modifier_images = modifier_images;
        }
    }

    public static class LoyalitypointsBean {
        /**
         * loyalty_points_value : 0.25
         * loyalty_points : 20
         * total_loyalty_points_value : 5
         */

        private double loyalty_points_value;
        private int loyalty_points;
        private int total_loyalty_points_value;

        public double getLoyalty_points_value() {
            return loyalty_points_value;
        }

        public void setLoyalty_points_value(double loyalty_points_value) {
            this.loyalty_points_value = loyalty_points_value;
        }

        public int getLoyalty_points() {
            return loyalty_points;
        }

        public void setLoyalty_points(int loyalty_points) {
            this.loyalty_points = loyalty_points;
        }

        public int getTotal_loyalty_points_value() {
            return total_loyalty_points_value;
        }

        public void setTotal_loyalty_points_value(int total_loyalty_points_value) {
            this.total_loyalty_points_value = total_loyalty_points_value;
        }
    }

    public static class ModifiersBean {
        /**
         * id : 20
         * product_id : 20
         * modifier_name : null
         * sku : 8898989
         * weight : 0
         * buy_price : 120.00
         * sell_price : 150.00
         * barcode : 9484489
         * quantity : 199
         * moq : 1
         * modifier_images : null
         * product_barcode_image : uploaded/barcodeImage/8559815730269910.png
         * product_qrcode_image : uploaded/qrcodeImage/5884115730269910.png
         * created_at : null
         * updated_at : null
         */

        private int id;
        private int product_id;
        private Object modifier_name;
        private String sku;
        private String weight;
        private String buy_price;
        private String sell_price;
        private String barcode;
        private int quantity;
        private int moq;
        private Object modifier_images;
        private String product_barcode_image;
        private String product_qrcode_image;
        private Object created_at;
        private Object updated_at;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
        }

        public Object getModifier_name() {
            return modifier_name;
        }

        public void setModifier_name(Object modifier_name) {
            this.modifier_name = modifier_name;
        }

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getBuy_price() {
            return buy_price;
        }

        public void setBuy_price(String buy_price) {
            this.buy_price = buy_price;
        }

        public String getSell_price() {
            return sell_price;
        }

        public void setSell_price(String sell_price) {
            this.sell_price = sell_price;
        }

        public String getBarcode() {
            return barcode;
        }

        public void setBarcode(String barcode) {
            this.barcode = barcode;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getMoq() {
            return moq;
        }

        public void setMoq(int moq) {
            this.moq = moq;
        }

        public Object getModifier_images() {
            return modifier_images;
        }

        public void setModifier_images(Object modifier_images) {
            this.modifier_images = modifier_images;
        }

        public String getProduct_barcode_image() {
            return product_barcode_image;
        }

        public void setProduct_barcode_image(String product_barcode_image) {
            this.product_barcode_image = product_barcode_image;
        }

        public String getProduct_qrcode_image() {
            return product_qrcode_image;
        }

        public void setProduct_qrcode_image(String product_qrcode_image) {
            this.product_qrcode_image = product_qrcode_image;
        }

        public Object getCreated_at() {
            return created_at;
        }

        public void setCreated_at(Object created_at) {
            this.created_at = created_at;
        }

        public Object getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(Object updated_at) {
            this.updated_at = updated_at;
        }
    }
}
