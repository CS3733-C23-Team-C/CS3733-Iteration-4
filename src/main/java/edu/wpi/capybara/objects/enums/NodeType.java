package edu.wpi.capybara.objects.enums;

public enum NodeType {
  HALL,
  LABS,
  REST,
  RETL,
  SERV,
  ELEV,
  EXIT,
  STAI,
  ERROR;

  public static NodeType strToNode(String node) {
    if (node.equals("HALL")) return NodeType.HALL;
    else if (node.equals("LABS")) return NodeType.LABS;
    else if (node.equals("REST")) return NodeType.REST;
    else if (node.equals("RETL")) return NodeType.RETL;
    else if (node.equals("SERV")) return NodeType.SERV;
    else if (node.equals("ELEV")) return NodeType.ELEV;
    else if (node.equals("EXIT")) return NodeType.EXIT;
    else if (node.equals("STAI")) return NodeType.STAI;
    else return NodeType.ERROR;
  }
}
