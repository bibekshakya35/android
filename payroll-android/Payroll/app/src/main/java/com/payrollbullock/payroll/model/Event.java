package com.payrollbullock.payroll.model;

/**
 * Created by bibekshakya on 7/15/16.
 */
public class Event {
    private int eventId;
    private String dateofEvent;
    private String header;
    private String place;
    private String content;
    private String specification;

    public int getEventId() {
        return eventId;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public String getDateofEvent() {
        return dateofEvent;
    }

    public void setDateofEvent(String dateofEvent) {
        this.dateofEvent = dateofEvent;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSpecification() {
        return specification;
    }

    public void setSpecification(String specification) {
        this.specification = specification;
    }
}
