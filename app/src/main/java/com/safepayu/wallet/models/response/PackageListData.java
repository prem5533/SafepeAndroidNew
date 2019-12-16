package com.safepayu.wallet.models.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PackageListData extends BaseResponse {
    @SerializedName("packages")
    List<Packages> packages;
    @SerializedName("tax")
    Tax tax;
    @SerializedName("bank_details")
    List<BankDetails> bankDetails;

    public PackageListData() {
    }

    public List<Packages> getPackages() {
        return packages;
    }

    public void setPackages(List<Packages> packages) {
        this.packages = packages;
    }

    public Tax getTax() {
        return tax;
    }

    public void setTax(Tax tax) {
        this.tax = tax;
    }

    public List<BankDetails> getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(List<BankDetails> bankDetails) {
        this.bankDetails = bankDetails;
    }

    public static class Packages {
        @SerializedName("id")
        String id;
        @SerializedName("package_name")
        String packageName;
        @SerializedName("package_amount")
        Double packageAmount;
        /**
         * image : uploaded/packagesImage/8635415763337990.jpg
         * description : If it didn't help
         */

        private String image;
        private String description;


        public Packages() {
        }

        public Packages(String id, String packageName, Double packageAmount) {
            this.id = id;
            this.packageName = packageName;
            this.packageAmount = packageAmount;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPackageName() {
            return packageName;
        }

        public void setPackageName(String packageName) {
            this.packageName = packageName;
        }

        public Double getPackageAmount() {
            return packageAmount;
        }

        public void setPackageAmount(Double packageAmount) {
            this.packageAmount = packageAmount;
        }

        @Override
        public String toString() {
            return "Packages{" +
                    "id='" + id + '\'' +
                    ", packageName='" + packageName + '\'' +
                    ", packageAmount=" + packageAmount +
                    '}';
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public class Tax{
        @SerializedName("id")
        String id;
        @SerializedName("tax_name")
        String taxName;
        @SerializedName("tax_value")
        String taxValue;

        public Tax() {
        }

        public Tax(String id, String taxName, String taxValue) {
            this.id = id;
            this.taxName = taxName;
            this.taxValue = taxValue;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTaxName() {
            return taxName;
        }

        public void setTaxName(String taxName) {
            this.taxName = taxName;
        }

        public String getTaxValue() {
            return taxValue;
        }

        public void setTaxValue(String taxValue) {
            this.taxValue = taxValue;
        }

        @Override
        public String toString() {
            return "Tax{" +
                    "id='" + id + '\'' +
                    ", taxName='" + taxName + '\'' +
                    ", taxValue='" + taxValue + '\'' +
                    '}';
        }
    }

    public class BankDetails{
        @SerializedName("id")
        String id;
        @SerializedName("bank_name")
        String bankName;
        @SerializedName("account_number")
        String accountNumber;
        @SerializedName("ifsc_code")
        String ifscCode;
        @SerializedName("account_holder_name")
        String accountHolderName;
        @SerializedName("bank_address")
        String bankAddress;
        @SerializedName("bank_logo")
        String bankLogo;
        @SerializedName("status")
        String status;

        public BankDetails() {
        }

        public BankDetails(String id, String bankName, String accountNumber, String ifscCode, String accountHolderName, String bankAddress, String bankLogo, String status) {
            this.id = id;
            this.bankName = bankName;
            this.accountNumber = accountNumber;
            this.ifscCode = ifscCode;
            this.accountHolderName = accountHolderName;
            this.bankAddress = bankAddress;
            this.bankLogo = bankLogo;
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBankName() {
            return bankName;
        }

        public void setBankName(String bankName) {
            this.bankName = bankName;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public String getIfscCode() {
            return ifscCode;
        }

        public void setIfscCode(String ifscCode) {
            this.ifscCode = ifscCode;
        }

        public String getAccountHolderName() {
            return accountHolderName;
        }

        public void setAccountHolderName(String accountHolderName) {
            this.accountHolderName = accountHolderName;
        }

        public String getBankAddress() {
            return bankAddress;
        }

        public void setBankAddress(String bankAddress) {
            this.bankAddress = bankAddress;
        }

        public String getBankLogo() {
            return bankLogo;
        }

        public void setBankLogo(String bankLogo) {
            this.bankLogo = bankLogo;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        @Override
        public String toString() {
            return "BankDetails{" +
                    "id='" + id + '\'' +
                    ", bankName='" + bankName + '\'' +
                    ", accountNumber='" + accountNumber + '\'' +
                    ", ifscCode='" + ifscCode + '\'' +
                    ", accountHolderName='" + accountHolderName + '\'' +
                    ", bankAddress='" + bankAddress + '\'' +
                    ", bankLogo='" + bankLogo + '\'' +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
