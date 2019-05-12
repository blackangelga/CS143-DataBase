/*
 * Copyright (C) 2016 Thomas Kercheval
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package CafeDansaDB;

/**
 * Dancer.java
 * Data Type describing a dancer with various information
 * attached to it. These Dancer objects will be used to populate
 * the database in our application.
 * <pre>
    Project: CafeDansa Database
    Platform: jdk 1.8.0_14; NetBeans IDE 8.1; Windows 10
    Course: CS 143
    Created on Apr 5, 2016, 1:26:19 PM
    Revised on Arp 12, 2016, 2:30:21 PM
 </pre>
 * @author Thomas Kercheval
 */
public class Dancer {

    /** index of dancers. */
    private int id;
    /** Name of the Dancer. */
    private String dancerName;
    /** Dance style of the Dancer. */
    private String style;
    /** Proficiency of the Dancer. */
    private String prof;
    /** Number of years the Dancer has been practicing. */
    private int years;
    /** Phone number of the Dancer. */
    private String phone;
    /** Email of the Dancer. */
    private String email;


    /**
     * Default constructor, sets everything to the default value.
     */
    public Dancer() {
        this.id = 0;
        this.dancerName = "";
        this.style = "";
        this.prof = "";
        this.years = 0;
        this.phone = "";
        this.email = "";
    }

    /**
     * Overloaded constructor, takes all Strings other than int for yearsExp.
     * @param id
     * @param name The name of our Dancer.
     * @param styleDance Style of dance of our Dancer.
     * @param proficiency Level of proficiency of our Dancer.
     * @param yearsExp Years our Dancer has been practicing.
     * @param phoneNum Phone number of our Dancer.
     * @param emailAdd Email address of our Dancer.
     */
    public Dancer(int id, String name, String styleDance, String proficiency,
           int yearsExp, String phoneNum, String emailAdd) {
        this.id = id;
        this.dancerName = name;
        this.style = styleDance;
        this.prof = proficiency;
        this.years = yearsExp;
        this.phone = phoneNum;
        this.email = emailAdd;
    }

//    /**
//     * Overloaded constructor, takes all Strings.
//     * @param name The name of our Dancer.
//     * @param danceStyle Style of dance of our Dancer.
//     * @param proficiency Level of proficiency of our Dancer.
//     * @param yearsExp Years our Dancer has been practicing.
//     * @param phoneNum Phone number of our Dancer.
//     * @param emailAdd Email address of our Dancer.
//     */
//    public Dancer(int id, String name, String danceStyle, String proficiency,
//          String yearsExp, String phoneNum, String emailAdd) {
//        this.id = id;
//        this.dancerName = name;
//        this.style = danceStyle;
//        this.prof = proficiency;
//        this.years = Integer.parseInt(yearsExp);
//        this.phone = phoneNum;
//        this.email = emailAdd;
//    }

    /**
     * Array constructor, takes an array of values as an input.
     * @param dancerInfo Array containing our Dancer's info. Ordered by 
     * static constants in this class.
     */
//    public Dancer(final String[] dancerInfo) {
//        this.dancerName = dancerInfo[NAME_INDEX];
//        this.style = dancerInfo[STYLE_INDEX];
//        this.prof = dancerInfo[PROF_INDEX];
//        this.years = Integer.parseInt(dancerInfo[YEARS_INDEX]);
//        this.phone = dancerInfo[PHONE_INDEX];
//        this.email = dancerInfo[EMAIL_INDEX];
//    }

    /**
     * Copy constructor, copies another Dancer object.
     * @param dancer Other dancer being cloned.
     */
//    public Dancer(final Dancer dancer) {
//        this.id = dancer.id;
//        this.dancerName = dancer.dancerName;
//        this.style = dancer.style;
//        this.prof = dancer.prof;
//        this.years = dancer.years;
//        this.phone = dancer.phone;
//    }
    
    public int getID()
    {
        return id;
    }
    
    public void setID(int id)
    {
        this.id = id;
    }
    /**
     *
     * @return The name of the Dancer.
     */
    public String getName() 
    {
        return dancerName;
    }

    /**
     *
     * @param nameDancer The name of the Dancer.
     */
    public void setDancerName(final String nameDancer) {
        this.dancerName = nameDancer;
    }

    /**
     *
     * @return The Style of Dance chosen by the Dancer.
     */
    public String getStyle() {
        return style;
    }

    /**
     *
     * @param danceStyle The Style of Dance chosen by the Dancer.
     */
    public void setStyle(final String danceStyle) {
        this.style = danceStyle;
    }

    /**
     *
     * @return Level of proficiency of the Dancer.
     */
    public String getProf() {
        return prof;
    }

    /**
     *
     * @param proficiency Level of proficiency of the Dancer.
     */
    public void setProf(final String proficiency) {
        this.prof = proficiency;
    }

    /**
     *
     * @return Number of years the Dancer has been practicing.
     */
    public int getYears() {
        return years;
    }

    /**
     *
     * @param yearsExp Number of years the Dancer has been practicing.
     */
    public void setYears(final int yearsExp) {
        this.years = yearsExp;
    }

    /**
     *
     * @return The Dancer's Phone Number.
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     * @param phoneNum The Dancer's Phone Number.
     */
    public void setPhone(final String phoneNum) {
        this.phone = phoneNum;
    }

    /**
     *
     * @return The Dancer's Email Address.
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param emailAdd The Dancer's Email Address.
     */
    public void setEmail(final String emailAdd) {
        this.email = emailAdd;
    }

    @Override
    /**
     *
     * @return String A String representation of our Dancer.
     */
    public String toString() {
        return "Dancer{" + "id=" + id + "dancerName=" + dancerName + ", style="
                + style + ", proficiency=" + prof + ", years=" + years
                + ", phone=" + phone + ", email=" + email + '}';
    }

    /**
     *
     * @param dancer Other Dancer we are comparing this Dancer to.
     * @return true if the dancers are equal.
     */
    public boolean equals(final Dancer dancer) {
        return this.getName().equalsIgnoreCase(dancer.getName())
               && this.getYears() == dancer.getYears()
                && this.getStyle().equalsIgnoreCase(dancer.getStyle());
    }

    @Override
    public int hashCode() {
        return this.dancerName.hashCode();
    }
}
