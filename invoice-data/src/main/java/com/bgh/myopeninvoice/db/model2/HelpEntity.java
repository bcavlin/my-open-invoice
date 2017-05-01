package com.bgh.myopeninvoice.db.model2;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Created by bcavlin on 29/04/17.
 */
@Entity
@Table(name = "HELP", schema = "INFORMATION_SCHEMA", catalog = "INVOICEDB")
public class HelpEntity {
    private Integer id;
    private String section;
    private String topic;
    private String syntax;
    private String text;

    @Basic
    @Column(name = "ID", nullable = true)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "SECTION", nullable = true, length = 2147483647)
    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @Basic
    @Column(name = "TOPIC", nullable = true, length = 2147483647)
    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Basic
    @Column(name = "SYNTAX", nullable = true, length = 2147483647)
    public String getSyntax() {
        return syntax;
    }

    public void setSyntax(String syntax) {
        this.syntax = syntax;
    }

    @Basic
    @Column(name = "TEXT", nullable = true, length = 2147483647)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HelpEntity that = (HelpEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (section != null ? !section.equals(that.section) : that.section != null) return false;
        if (topic != null ? !topic.equals(that.topic) : that.topic != null) return false;
        if (syntax != null ? !syntax.equals(that.syntax) : that.syntax != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (section != null ? section.hashCode() : 0);
        result = 31 * result + (topic != null ? topic.hashCode() : 0);
        result = 31 * result + (syntax != null ? syntax.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}
