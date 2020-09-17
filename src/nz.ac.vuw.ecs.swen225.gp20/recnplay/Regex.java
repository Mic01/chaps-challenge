package nz.ac.vuw.ecs.swen225.gp20.recnplay;

import java.util.regex.Pattern;

class Regex {
  public static final Pattern scanDelim = Pattern.compile("\\s+|(?=[{}(),;])|(?<=[{}(),;])");
  public static final Pattern name = Pattern.compile(".*\"[a-z]*\"*:");
}
