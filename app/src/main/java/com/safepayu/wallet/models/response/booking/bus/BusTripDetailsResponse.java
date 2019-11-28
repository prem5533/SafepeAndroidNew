package com.safepayu.wallet.models.response.booking.bus;

import java.util.List;

public class BusTripDetailsResponse {

    /**
     * status : true
     * mesage : Data retrieved successfully
     * data : {"TripId":"86","Seats":[{"Column":0,"Fare":"102","NetFare":"102","IsAvailableSeat":"True","IsLadiesSeat":"False","Length":1,"Number":"1","Row":4,"Width":1,"Zindex":0,"Servicetax":"0","OperatorServiceCharge":"0","SeatCode":"0S1"}],"SeatsScript":null,"LayoutType":"","BoardingTimes":null,"DroppingTimes":null,"Provider":"DZ/GmxpuN0rTfFNIdYpjWA==","ResponseStatus":200,"Message":null}
     */

    private boolean status;
    private String mesage;
    private DataBean data;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMesage() {
        return mesage;
    }

    public void setMesage(String mesage) {
        this.mesage = mesage;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * TripId : 86
         * Seats : [{"Column":0,"Fare":"102","NetFare":"102","IsAvailableSeat":"True","IsLadiesSeat":"False","Length":1,"Number":"1","Row":4,"Width":1,"Zindex":0,"Servicetax":"0","OperatorServiceCharge":"0","SeatCode":"0S1"}]
         * SeatsScript : null
         * LayoutType :
         * BoardingTimes : null
         * DroppingTimes : null
         * Provider : DZ/GmxpuN0rTfFNIdYpjWA==
         * ResponseStatus : 200
         * Message : null
         */

        private String TripId;
        private Object SeatsScript;
        private String LayoutType;
        private Object BoardingTimes;
        private Object DroppingTimes;
        private String Provider;
        private int ResponseStatus;
        private Object Message;
        private List<SeatsBean> Seats;

        public String getTripId() {
            return TripId;
        }

        public void setTripId(String TripId) {
            this.TripId = TripId;
        }

        public Object getSeatsScript() {
            return SeatsScript;
        }

        public void setSeatsScript(Object SeatsScript) {
            this.SeatsScript = SeatsScript;
        }

        public String getLayoutType() {
            return LayoutType;
        }

        public void setLayoutType(String LayoutType) {
            this.LayoutType = LayoutType;
        }

        public Object getBoardingTimes() {
            return BoardingTimes;
        }

        public void setBoardingTimes(Object BoardingTimes) {
            this.BoardingTimes = BoardingTimes;
        }

        public Object getDroppingTimes() {
            return DroppingTimes;
        }

        public void setDroppingTimes(Object DroppingTimes) {
            this.DroppingTimes = DroppingTimes;
        }

        public String getProvider() {
            return Provider;
        }

        public void setProvider(String Provider) {
            this.Provider = Provider;
        }

        public int getResponseStatus() {
            return ResponseStatus;
        }

        public void setResponseStatus(int ResponseStatus) {
            this.ResponseStatus = ResponseStatus;
        }

        public Object getMessage() {
            return Message;
        }

        public void setMessage(Object Message) {
            this.Message = Message;
        }

        public List<SeatsBean> getSeats() {
            return Seats;
        }

        public void setSeats(List<SeatsBean> Seats) {
            this.Seats = Seats;
        }

        public static class SeatsBean {
            /**
             * Column : 0
             * Fare : 102
             * NetFare : 102
             * IsAvailableSeat : True
             * IsLadiesSeat : False
             * Length : 1
             * Number : 1
             * Row : 4
             * Width : 1
             * Zindex : 0
             * Servicetax : 0
             * OperatorServiceCharge : 0
             * SeatCode : 0S1
             */

            private int Column;
            private String Fare;
            private String NetFare;
            private String IsAvailableSeat;
            private String IsLadiesSeat;
            private int Length;
            private String Number;
            private int Row;
            private int Width;
            private int Zindex;
            private String Servicetax;
            private String OperatorServiceCharge;
            private String SeatCode;

            public int getColumn() {
                return Column;
            }

            public void setColumn(int Column) {
                this.Column = Column;
            }

            public String getFare() {
                return Fare;
            }

            public void setFare(String Fare) {
                this.Fare = Fare;
            }

            public String getNetFare() {
                return NetFare;
            }

            public void setNetFare(String NetFare) {
                this.NetFare = NetFare;
            }

            public String getIsAvailableSeat() {
                return IsAvailableSeat;
            }

            public void setIsAvailableSeat(String IsAvailableSeat) {
                this.IsAvailableSeat = IsAvailableSeat;
            }

            public String getIsLadiesSeat() {
                return IsLadiesSeat;
            }

            public void setIsLadiesSeat(String IsLadiesSeat) {
                this.IsLadiesSeat = IsLadiesSeat;
            }

            public int getLength() {
                return Length;
            }

            public void setLength(int Length) {
                this.Length = Length;
            }

            public String getNumber() {
                return Number;
            }

            public void setNumber(String Number) {
                this.Number = Number;
            }

            public int getRow() {
                return Row;
            }

            public void setRow(int Row) {
                this.Row = Row;
            }

            public int getWidth() {
                return Width;
            }

            public void setWidth(int Width) {
                this.Width = Width;
            }

            public int getZindex() {
                return Zindex;
            }

            public void setZindex(int Zindex) {
                this.Zindex = Zindex;
            }

            public String getServicetax() {
                return Servicetax;
            }

            public void setServicetax(String Servicetax) {
                this.Servicetax = Servicetax;
            }

            public String getOperatorServiceCharge() {
                return OperatorServiceCharge;
            }

            public void setOperatorServiceCharge(String OperatorServiceCharge) {
                this.OperatorServiceCharge = OperatorServiceCharge;
            }

            public String getSeatCode() {
                return SeatCode;
            }

            public void setSeatCode(String SeatCode) {
                this.SeatCode = SeatCode;
            }
        }
    }
}
