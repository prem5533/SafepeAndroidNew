package com.safepayu.wallet.ecommerce.model.response;

import java.util.List;

public class ProductsDetailsResponse {


    /**
     * status : true
     * message : Product Details
     * venues : {"id":2,"merchant_id":2,"venue_id":"201911011148462","venue_name":"LillyWhites","venue_images":["1572608926.a-lillywhites-store-in-piccadilly-circus-london-england-uk-C5RJXF.jpg","20191121064555.lillywhites.jpg","20191121064555.lillywhites-sports-shopdepartment-store-londons-west-end-london-engtand-DA53PJ.jpg"],"address_1":"Oaklands Road, Wolverhampton WV3 0DS, England, UK","address_2":null,"city_name":"Wolverhampton","country":"United Kingdom","post_code":"WV3 0DS","phone_number":"5468496484","venue_email":"whiteslilly@dmailpro.net","venue_website":"https://www.lillywhites.com/","home_delivery_mov":"0.30","start_days":1,"end_days":2,"collection_time":20,"preparation_time":30,"free_delivery":"0.30","delivery_charge":"0.30","delivery":1,"collection":1,"opening_time":"06:15","closing_time":"20:30"}
     * products : {"id":70,"merchant_id":2,"venue_id":"201911011148462","product_name":"Women Sea Green & Blue Striped Kurta with Palazzos","product_description":"<p><span style=\"color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\">Sea green, blue and red striped kurta with palazzos<\/span><br style=\"box-sizing: inherit; color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\"><span style=\"color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\">Sea green, blue and red striped straight calf length kurta, has a keyhole neck, short sleeves, straight hem, side slits<\/span><br style=\"box-sizing: inherit; color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\"><span style=\"color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\">Sea green and red printed palazzos, has partially elasticated waistband, slip-on closure, two pockets<\/span><br><\/p>","measurement_unit_id":2,"category_id":["7","2"],"related_product_ids":["71","72"],"brand_id":8,"tax_id":"5","supplier_id":2,"product_tags":"Kurta","images":["uploaded/products/5632215760571460.jpeg","uploaded/products/3487215760571461.jpeg","uploaded/products/3855815760571462.jpeg"],"modifier_list":{"Color":["Gerua"],"Size":["M","L","XL"]},"modifier_id":135,"modifier_name":"Gerua,M","buy_price":"10.00","selling_price":"21.00","avl_quantity":91,"modifier_images":["uploaded/products/4850015760573040.jpeg","uploaded/products/1411915760573040.jpeg","uploaded/products/9070115760573040.jpeg",""],"productsRating":0}
     * productOfers : [{"offer_id":1,"offer_type":"discper","disc_per":"10.00","disc_amt":"0.00","offer_title":"10% Off","start_date":"2019-10-31","end_date":"2020-11-15","offer_time_start":"00:05:00","offer_time_end":"23:55:00","type":1,"discount_type":1,"discount_amount":"10.00","product_id":70,"merchant_id":2,"venue_id":"201911011148462","product_name":"Women Sea Green & Blue Striped Kurta with Palazzos","product_description":"<p><span style=\"color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\">Sea green, blue and red striped kurta with palazzos<\/span><br style=\"box-sizing: inherit; color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\"><span style=\"color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\">Sea green, blue and red striped straight calf length kurta, has a keyhole neck, short sleeves, straight hem, side slits<\/span><br style=\"box-sizing: inherit; color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\"><span style=\"color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\">Sea green and red printed palazzos, has partially elasticated waistband, slip-on closure, two pockets<\/span><br><\/p>","product_images":"uploaded/products/5632215760571460.jpeg","modifier_id":135,"buy_price":"10.00","selling_price":"21.00","avl_quantity":91,"modifier_images":"uploaded/products/4850015760573040.jpeg","venue_name":"LillyWhites","distance":"3,634.56","stars":0}]
     * relatedproduct : [{"id":71,"product_name":"Women Cream-Coloured & Pink Printed Maxi Shirt Dress","product_description":"<p><span style=\"color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\">Cream-Coloured and pink printed woven maxi shirt dress, has a shirt collar, three-quarter sleeves, hook and eye closures, flared hem<\/span><br style=\"box-sizing: inherit; color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\"><span style=\"color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;\">Comes with a belt<\/span><br><\/p>","merchant_id":2,"category_id":["2"],"brand_id":8,"images":"uploaded/products/1192015760662200.png","modifier_id":138,"selling_price":"100.00","buy_price":"70.00","avl_quantity":99,"modifier_images":null,"venue_id":"201911011148462","venue_name":"LillyWhites","latitude":"52.5756465","longitude":"-2.1385980000000018","productRating":0,"distance":"3,634.56"}]
     * loyalitypoints : {"loyalty_points_value":0.25,"loyalty_points":0,"total_loyalty_points_value":0}
     * modifiers : [{"id":135,"product_id":70,"modifier_name":"Gerua,M","sku":"02566","weight":"10","buy_price":"10.00","sell_price":"21.00","barcode":"120145632","quantity":91,"moq":1,"modifier_images":"uploaded/products/4850015760573040.jpeg,uploaded/products/1411915760573040.jpeg,uploaded/products/9070115760573040.jpeg,","product_barcode_image":"uploaded/barcodeImage/2102615760571460.png","product_qrcode_image":"uploaded/qrcodeImage/6240415760571460.png","created_at":null,"updated_at":null},{"id":136,"product_id":70,"modifier_name":"Gerua,L","sku":"02566","weight":"10","buy_price":"11.00","sell_price":"22.00","barcode":"120145632","quantity":100,"moq":1,"modifier_images":"uploaded/products/7620815771944890.jpeg,,,","product_barcode_image":"uploaded/barcodeImage/1098015760571460.png","product_qrcode_image":"uploaded/qrcodeImage/9961915760571460.png","created_at":null,"updated_at":null},{"id":137,"product_id":70,"modifier_name":"Gerua,XL","sku":"02566","weight":"10","buy_price":"12.00","sell_price":"23.00","barcode":"120145632","quantity":99,"moq":1,"modifier_images":"uploaded/products/7417515771945230.jpeg,,,","product_barcode_image":"uploaded/barcodeImage/5981915760571460.png","product_qrcode_image":"uploaded/qrcodeImage/1897815760571460.png","created_at":null,"updated_at":null}]
     */

    private boolean status;
    private String message;
    private VenuesBean venues;
    private ProductsBean products;
    private LoyalitypointsBean loyalitypoints;
    private List<ProductOfersBean> productOfers;
    private List<RelatedproductBean> relatedproduct;
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

    public List<ProductOfersBean> getProductOfers() {
        return productOfers;
    }

    public void setProductOfers(List<ProductOfersBean> productOfers) {
        this.productOfers = productOfers;
    }

    public List<RelatedproductBean> getRelatedproduct() {
        return relatedproduct;
    }

    public void setRelatedproduct(List<RelatedproductBean> relatedproduct) {
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
         * id : 70
         * merchant_id : 2
         * venue_id : 201911011148462
         * product_name : Women Sea Green & Blue Striped Kurta with Palazzos
         * product_description : <p><span style="color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;">Sea green, blue and red striped kurta with palazzos</span><br style="box-sizing: inherit; color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;"><span style="color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;">Sea green, blue and red striped straight calf length kurta, has a keyhole neck, short sleeves, straight hem, side slits</span><br style="box-sizing: inherit; color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;"><span style="color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;">Sea green and red printed palazzos, has partially elasticated waistband, slip-on closure, two pockets</span><br></p>
         * measurement_unit_id : 2
         * category_id : ["7","2"]
         * related_product_ids : ["71","72"]
         * brand_id : 8
         * tax_id : 5
         * supplier_id : 2
         * product_tags : Kurta
         * images : ["uploaded/products/5632215760571460.jpeg","uploaded/products/3487215760571461.jpeg","uploaded/products/3855815760571462.jpeg"]
         * modifier_list : {"Color":["Gerua"],"Size":["M","L","XL"]}
         * modifier_id : 135
         * modifier_name : Gerua,M
         * buy_price : 10.00
         * selling_price : 21.00
         * avl_quantity : 91
         * modifier_images : ["uploaded/products/4850015760573040.jpeg","uploaded/products/1411915760573040.jpeg","uploaded/products/9070115760573040.jpeg",""]
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
        private ModifierListBean modifier_list;
        private int modifier_id;
        private String modifier_name;
        private String buy_price;
        private String selling_price;
        private int avl_quantity;
        private int productsRating;
        private List<String> category_id;
        private List<String> related_product_ids;
        private List<String> images;
        private List<String> modifier_images;

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

        public ModifierListBean getModifier_list() {
            return modifier_list;
        }

        public void setModifier_list(ModifierListBean modifier_list) {
            this.modifier_list = modifier_list;
        }

        public int getModifier_id() {
            return modifier_id;
        }

        public void setModifier_id(int modifier_id) {
            this.modifier_id = modifier_id;
        }

        public String getModifier_name() {
            return modifier_name;
        }

        public void setModifier_name(String modifier_name) {
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

        public List<String> getRelated_product_ids() {
            return related_product_ids;
        }

        public void setRelated_product_ids(List<String> related_product_ids) {
            this.related_product_ids = related_product_ids;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }

        public List<String> getModifier_images() {
            return modifier_images;
        }

        public void setModifier_images(List<String> modifier_images) {
            this.modifier_images = modifier_images;
        }

        public static class ModifierListBean {
            private List<String> Color;
            private List<String> Size;

            public List<String> getColor() {
                return Color;
            }

            public void setColor(List<String> Color) {
                this.Color = Color;
            }

            public List<String> getSize() {
                return Size;
            }

            public void setSize(List<String> Size) {
                this.Size = Size;
            }
        }
    }

    public static class LoyalitypointsBean {
        /**
         * loyalty_points_value : 0.25
         * loyalty_points : 0
         * total_loyalty_points_value : 0
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

    public static class ProductOfersBean {
        /**
         * offer_id : 1
         * offer_type : discper
         * disc_per : 10.00
         * disc_amt : 0.00
         * offer_title : 10% Off
         * start_date : 2019-10-31
         * end_date : 2020-11-15
         * offer_time_start : 00:05:00
         * offer_time_end : 23:55:00
         * type : 1
         * discount_type : 1
         * discount_amount : 10.00
         * product_id : 70
         * merchant_id : 2
         * venue_id : 201911011148462
         * product_name : Women Sea Green & Blue Striped Kurta with Palazzos
         * product_description : <p><span style="color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;">Sea green, blue and red striped kurta with palazzos</span><br style="box-sizing: inherit; color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;"><span style="color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;">Sea green, blue and red striped straight calf length kurta, has a keyhole neck, short sleeves, straight hem, side slits</span><br style="box-sizing: inherit; color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;"><span style="color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;">Sea green and red printed palazzos, has partially elasticated waistband, slip-on closure, two pockets</span><br></p>
         * product_images : uploaded/products/5632215760571460.jpeg
         * modifier_id : 135
         * buy_price : 10.00
         * selling_price : 21.00
         * avl_quantity : 91
         * modifier_images : uploaded/products/4850015760573040.jpeg
         * venue_name : LillyWhites
         * distance : 3,634.56
         * stars : 0
         */

        private int offer_id;
        private String offer_type;
        private String disc_per;
        private String disc_amt;
        private String offer_title;
        private String start_date;
        private String end_date;
        private String offer_time_start;
        private String offer_time_end;
        private int type;
        private int discount_type;
        private String discount_amount;
        private int product_id;
        private int merchant_id;
        private String venue_id;
        private String product_name;
        private String product_description;
        private String product_images;
        private int modifier_id;
        private String buy_price;
        private String selling_price;
        private int avl_quantity;
        private String modifier_images;
        private String venue_name;
        private String distance;
        private int stars;

        public int getOffer_id() {
            return offer_id;
        }

        public void setOffer_id(int offer_id) {
            this.offer_id = offer_id;
        }

        public String getOffer_type() {
            return offer_type;
        }

        public void setOffer_type(String offer_type) {
            this.offer_type = offer_type;
        }

        public String getDisc_per() {
            return disc_per;
        }

        public void setDisc_per(String disc_per) {
            this.disc_per = disc_per;
        }

        public String getDisc_amt() {
            return disc_amt;
        }

        public void setDisc_amt(String disc_amt) {
            this.disc_amt = disc_amt;
        }

        public String getOffer_title() {
            return offer_title;
        }

        public void setOffer_title(String offer_title) {
            this.offer_title = offer_title;
        }

        public String getStart_date() {
            return start_date;
        }

        public void setStart_date(String start_date) {
            this.start_date = start_date;
        }

        public String getEnd_date() {
            return end_date;
        }

        public void setEnd_date(String end_date) {
            this.end_date = end_date;
        }

        public String getOffer_time_start() {
            return offer_time_start;
        }

        public void setOffer_time_start(String offer_time_start) {
            this.offer_time_start = offer_time_start;
        }

        public String getOffer_time_end() {
            return offer_time_end;
        }

        public void setOffer_time_end(String offer_time_end) {
            this.offer_time_end = offer_time_end;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getDiscount_type() {
            return discount_type;
        }

        public void setDiscount_type(int discount_type) {
            this.discount_type = discount_type;
        }

        public String getDiscount_amount() {
            return discount_amount;
        }

        public void setDiscount_amount(String discount_amount) {
            this.discount_amount = discount_amount;
        }

        public int getProduct_id() {
            return product_id;
        }

        public void setProduct_id(int product_id) {
            this.product_id = product_id;
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

        public String getProduct_images() {
            return product_images;
        }

        public void setProduct_images(String product_images) {
            this.product_images = product_images;
        }

        public int getModifier_id() {
            return modifier_id;
        }

        public void setModifier_id(int modifier_id) {
            this.modifier_id = modifier_id;
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

        public String getModifier_images() {
            return modifier_images;
        }

        public void setModifier_images(String modifier_images) {
            this.modifier_images = modifier_images;
        }

        public String getVenue_name() {
            return venue_name;
        }

        public void setVenue_name(String venue_name) {
            this.venue_name = venue_name;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public int getStars() {
            return stars;
        }

        public void setStars(int stars) {
            this.stars = stars;
        }
    }

    public static class RelatedproductBean {
        /**
         * id : 71
         * product_name : Women Cream-Coloured & Pink Printed Maxi Shirt Dress
         * product_description : <p><span style="color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;">Cream-Coloured and pink printed woven maxi shirt dress, has a shirt collar, three-quarter sleeves, hook and eye closures, flared hem</span><br style="box-sizing: inherit; color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;"><span style="color: rgb(40, 44, 63); font-family: Whitney; font-size: 16px;">Comes with a belt</span><br></p>
         * merchant_id : 2
         * category_id : ["2"]
         * brand_id : 8
         * images : uploaded/products/1192015760662200.png
         * modifier_id : 138
         * selling_price : 100.00
         * buy_price : 70.00
         * avl_quantity : 99
         * modifier_images : null
         * venue_id : 201911011148462
         * venue_name : LillyWhites
         * latitude : 52.5756465
         * longitude : -2.1385980000000018
         * productRating : 0
         * distance : 3,634.56
         */

        private int id;
        private String product_name;
        private String product_description;
        private int merchant_id;
        private int brand_id;
        private String images;
        private int modifier_id;
        private String selling_price;
        private String buy_price;
        private int avl_quantity;
        private Object modifier_images;
        private String venue_id;
        private String venue_name;
        private String latitude;
        private String longitude;
        private int productRating;
        private String distance;
        private List<String> category_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public int getMerchant_id() {
            return merchant_id;
        }

        public void setMerchant_id(int merchant_id) {
            this.merchant_id = merchant_id;
        }

        public int getBrand_id() {
            return brand_id;
        }

        public void setBrand_id(int brand_id) {
            this.brand_id = brand_id;
        }

        public String getImages() {
            return images;
        }

        public void setImages(String images) {
            this.images = images;
        }

        public int getModifier_id() {
            return modifier_id;
        }

        public void setModifier_id(int modifier_id) {
            this.modifier_id = modifier_id;
        }

        public String getSelling_price() {
            return selling_price;
        }

        public void setSelling_price(String selling_price) {
            this.selling_price = selling_price;
        }

        public String getBuy_price() {
            return buy_price;
        }

        public void setBuy_price(String buy_price) {
            this.buy_price = buy_price;
        }

        public int getAvl_quantity() {
            return avl_quantity;
        }

        public void setAvl_quantity(int avl_quantity) {
            this.avl_quantity = avl_quantity;
        }

        public Object getModifier_images() {
            return modifier_images;
        }

        public void setModifier_images(Object modifier_images) {
            this.modifier_images = modifier_images;
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

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public int getProductRating() {
            return productRating;
        }

        public void setProductRating(int productRating) {
            this.productRating = productRating;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public List<String> getCategory_id() {
            return category_id;
        }

        public void setCategory_id(List<String> category_id) {
            this.category_id = category_id;
        }
    }

    public static class ModifiersBean {
        /**
         * id : 135
         * product_id : 70
         * modifier_name : Gerua,M
         * sku : 02566
         * weight : 10
         * buy_price : 10.00
         * sell_price : 21.00
         * barcode : 120145632
         * quantity : 91
         * moq : 1
         * modifier_images : uploaded/products/4850015760573040.jpeg,uploaded/products/1411915760573040.jpeg,uploaded/products/9070115760573040.jpeg,
         * product_barcode_image : uploaded/barcodeImage/2102615760571460.png
         * product_qrcode_image : uploaded/qrcodeImage/6240415760571460.png
         * created_at : null
         * updated_at : null
         */

        private int id;
        private int product_id;
        private String modifier_name;
        private String sku;
        private String weight;
        private String buy_price;
        private String sell_price;
        private String barcode;
        private int quantity;
        private int moq;
        private String modifier_images;
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

        public String getModifier_name() {
            return modifier_name;
        }

        public void setModifier_name(String modifier_name) {
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

        public String getModifier_images() {
            return modifier_images;
        }

        public void setModifier_images(String modifier_images) {
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
