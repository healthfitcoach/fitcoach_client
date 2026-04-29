package distbuted_programming.domain.equipment;

import java.util.ArrayList;
import java.util.List;

public class Equipment {

  private String equipmentId;
  private String name;
  private String category;
  private String image;
  private String description;
  private String status;

  private static final List<Equipment> equipmentList = new ArrayList<>();

  public Equipment() {}

  public Equipment(String equipmentId, String name, String category,
      String image, String description, String status) {
    this.equipmentId = equipmentId;
    this.name = name;
    this.category = category;
    this.image = image;
    this.description = description;
    this.status = status;
  }

  public List<Equipment> getList(String category) {
    if (category == null || category.isEmpty()) {
      return equipmentList;
    }
    List<Equipment> result = new ArrayList<>();
    for (Equipment e : equipmentList) {
      if (e.category.equals(category)) {
        result.add(e);
      }
    }
    return result;
  }

  public List<Equipment> search(String keyword) {
    List<Equipment> result = new ArrayList<>();
    for (Equipment e : equipmentList) {
      if (e.name.contains(keyword) || e.description.contains(keyword)
          || e.category.contains(keyword)) {
        result.add(e);
      }
    }
    return result;
  }

  public Equipment get() {
    return this;
  }

  public String getEquipmentId() { return equipmentId; }
  public String getName() { return name; }
  public String getCategory() { return category; }
  public String getImage() { return image; }
  public String getDescription() { return description; }
  public String getStatus() { return status; }

  public static List<Equipment> getAll() { return equipmentList; }
  public static void add(Equipment e) { equipmentList.add(e); }

  public static Equipment findById(String equipmentId) {
    for (Equipment e : equipmentList) {
      if (e.equipmentId.equals(equipmentId)) {
        return e;
      }
    }
    return null;
  }

  @Override
  public String toString() {
    return "[" + category + "] " + name + " | " + description + " | 상태: " + status;
  }
}
