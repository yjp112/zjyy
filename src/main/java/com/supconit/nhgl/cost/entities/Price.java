
package com.supconit.nhgl.cost.entities;

import hc.base.domains.LongId;
public class Price extends LongId{
	
    	private static final long	serialVersionUID	= 1L;
        
        private Long year;
        private Long month;
        private Long yearMonth;
        private Double electric;
        private Double water;
        private Double energy;
        private Double gas;
        private Long startYear;
        private Long endYear;

        private static Price DEFAULT_PRICE = new Price();
        
		public Double getEnergy() {
			return energy;
		}
		public void setEnergy(Double energy) {
			this.energy = energy;
		}
		public Long getYear() {
			return year;
		}
		public void setYear(Long year) {
			this.year = year;
		}
		
		public Long getStartYear() {
			return startYear;
		}
		public void setStartYear(Long startYear) {
			this.startYear = startYear;
		}
		public Long getEndYear() {
			return endYear;
		}
		public void setEndYear(Long endYear) {
			this.endYear = endYear;
		}
		public Long getMonth() {
			return month;
		}
		public void setMonth(Long month) {
			this.month = month;
		}
		public Long getYearMonth() {
			return yearMonth;
		}
		public void setYearMonth(Long yearMonth) {
			this.yearMonth = yearMonth;
		}
		public Double getWater() {
			return water;
		}
		public void setWater(Double water) {
			this.water = water;
		}
		public Double getGas() {
			return gas;
		}
		public void setGas(Double gas) {
			this.gas = gas;
		}
		

		public Double getElectric() {
			return electric;
		}
		public void setElectric(Double electric) {
			this.electric = electric;
		}
		
		
	public static Price getDefaultPrice(){
        DEFAULT_PRICE.setWater(1D);
        DEFAULT_PRICE.setGas(1D);
        DEFAULT_PRICE.setElectric(1D);
        DEFAULT_PRICE.setEnergy(1D);
        return DEFAULT_PRICE;
    }


    @Override
    public String toString() {
        return "Price{" +
                "year=" + year +
                ", month=" + month +
                ", yearMonth=" + yearMonth +
                ", electric=" + electric +
                ", water=" + water +
                ", energy=" + energy +
                ", gas=" + gas +
                ", startYear=" + startYear +
                ", endYear=" + endYear +
                '}';
    }
}

