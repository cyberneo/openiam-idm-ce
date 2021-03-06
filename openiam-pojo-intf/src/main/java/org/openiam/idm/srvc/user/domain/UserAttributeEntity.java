package org.openiam.idm.srvc.user.domain;

import org.hibernate.annotations.GenericGenerator;
import org.openiam.dozer.DozerDTOCorrespondence;
import org.openiam.idm.srvc.user.dto.UserAttribute;

import javax.persistence.*;

@Entity
@Table(name = "USER_ATTRIBUTES")
@DozerDTOCorrespondence(UserAttribute.class)
public class UserAttributeEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    @Column(name = "ID", length = 32, nullable = false)
    private String id;

    @Column(name = "METADATA_ID", length = 20)
    private String metadataElementId;

    @Column(name = "NAME", length = 50)
    private String name;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;

    @Column(name = "VALUE", length = 1000)
    private String value;


    public UserAttributeEntity() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMetadataElementId() {
        return metadataElementId;
    }

    public void setMetadataElementId(String metadataElementId) {
        this.metadataElementId = metadataElementId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAttributeEntity)) return false;

        UserAttributeEntity that = (UserAttributeEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (metadataElementId != null ? !metadataElementId.equals(that.metadataElementId) : that.metadataElementId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (user != null ? !user.equals(that.user) : that.user != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (metadataElementId != null ? metadataElementId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (user != null ? user.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }



}
