package org.example.Models;

import java.util.HashSet;
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

  @ElementCollection(targetClass = Area.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "project_area", joinColumns = @JoinColumn(name = "project_id"))
  @Enumerated(EnumType.STRING)
  private Set<Area> areas = new HashSet<>();

  @ManyToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
  @JoinTable
  private Set<User> user = new HashSet<>();

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
