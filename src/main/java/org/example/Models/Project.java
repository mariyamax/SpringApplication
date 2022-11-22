package org.example.Models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.example.Enums.Area;
import org.hibernate.Hibernate;

@Entity
@Table(name = "projects")
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Project {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long ID;

  @Column(name = "name",unique = true)
  private String name;

  @Column(name = "description")
  private String description;

  @ElementCollection(targetClass = User.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "project_users", joinColumns = @JoinColumn(name = "project_id"))
  private List<User> users = new ArrayList<>();

  @ElementCollection(targetClass = Area.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "project_area", joinColumns = @JoinColumn(name = "project_id"))
  @Enumerated(EnumType.STRING)
  private Set<Area> areas = new HashSet<>();

  @Column(name="admin_id")
  private Long adminId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
      return false;
    }
    Project project = (Project) o;
    return ID != null && Objects.equals(ID, project.ID);
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }
}
