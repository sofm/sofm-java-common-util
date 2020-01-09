package com.github.sofm.common.util.core;

import static org.zeroturnaround.zip.commons.FileUtilsV2_2.deleteDirectory;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.zeroturnaround.zip.ZipUtil;

@Slf4j
public class FileUtil {

  private FileUtil() {
  }

  public static void changeModInLinux(String path) {
    changeModInLinux(path, "777");
  }

  public static void changeModInLinux(String path, String mode) {
    try {
      String command = "chmod -R " + mode + " " + path;
      if (SystemUtil.isUnix()) {
        Runtime.getRuntime().exec(command);
      } else {
        log.info("(changeModInLinux)NOT_SUPPORT.");
      }
    } catch (Exception ex) {
      log.error("(changeModInLinux)ex: {}", ExceptionUtil.getFullStackTrace(ex));
    }
  }

  public static String createDateDirectory(String rootDirectory) {
    Calendar calendar = Calendar.getInstance();
    String yearPath = rootDirectory + "/" + calendar.get(Calendar.YEAR);
    String monthPath = yearPath + "/" + getMonthInHumanView(calendar.get(Calendar.MONTH));
    String datePath = monthPath + "/" + calendar.get(Calendar.DAY_OF_MONTH);
    createDirectory(datePath);
    return datePath;
  }

  public static void createDirectory(String directoryPath, FileAttribute<?>... attrs) {
    try {
      if (!(new File(directoryPath)).exists()) {
        Path path = Paths.get(directoryPath);
        if (SystemUtil.isUnix()) {
          Files.createDirectories(path, attrs);
        } else {
          Files.createDirectories(path);
        }
      } else {
        log.info("(createDirectory)DIRECTORY_ALREADY_EXISTS, directoryPath: {}", directoryPath);
      }
    } catch (IOException ex) {
      log.error(
          "(createDirectory)CREATE_DIRECTORY_FAILED, ex: {}", ExceptionUtil.getFullStackTrace(ex));
    }
  }

  public static void createFile(byte[] bytes, String filepath, FileAttribute<?>... attrs) {
    try {
      Path path = Paths.get(filepath);
      Files.createFile(path, attrs);
      Files.write(path, bytes);
    } catch (IOException ex) {
      log.error("(createFile)ex: {}", ExceptionUtil.getFullStackTrace(ex));
    }
  }

  public static String getExtension(String uri) {
    return uri.substring(uri.lastIndexOf('.'));
  }

  public static String getExtensionWithoutDot(String uri) {
    return uri.substring(uri.lastIndexOf('.')).replace(".", "");
  }

  public static String getFilename(String filepath) {
    Path path = Paths.get(filepath);
    return path.getFileName().toString();
  }

  public static String getFilenameWithoutExtension(String filepath) {
    String filename = getFilename(filepath);
    if (filename == null) {
      return null;
    } else {
      int dotIndex = filename.lastIndexOf(".");
      return dotIndex > 0 ? filename.substring(0, dotIndex) : filename;
    }
  }

  public static double getFileSize(String path) {
    File file = new File(path);
    if (file.exists()) {
      double bytes = (double) file.length();
      double kilobytes = bytes / 1024.0D;
      return kilobytes / 1024.0D;
    } else {
      return 0.0D;
    }
  }

  public static String getParent(String filepath) {
    Path file = Paths.get(filepath);
    Path parent = file.getParent();
    return parent == null ? null : parent.toString();
  }

  public static String searchStringInFile(String filePath, String keyword) {
    String result = "";

    try (Stream<String> lines = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      for (String line : (Iterable<String>) lines::iterator) {
        if (line.contains(keyword)) {
          return line;
        }
      }
    } catch (Exception ex) {
      log.error("(searchStringInFile)ex:  {}", ExceptionUtil.getFullStackTrace(ex));
    }

    return result;
  }

  public static List<String> toList(String filePath) {
    List<String> result = new ArrayList<>();

    try (Stream<String> lines = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
      for (String line : (Iterable<String>) lines::iterator) {
        result.add(line);
      }
    } catch (Exception ex) {
      log.error("(toList)ex: {}", ExceptionUtil.getFullStackTrace(ex));
    }

    return result;
  }

  public static void unzipFile(String zipFilepath, FileAttribute<?>... attrs) throws IOException {
    String parent = getParent(zipFilepath);
    String zipFolder = parent + "/" + getFilenameWithoutExtension(zipFilepath) + "/";
    deleteDirectory(new File(zipFolder));
    Files.createDirectories(Paths.get(zipFolder), attrs);
    ZipUtil.unpack(new File(zipFilepath), new File(zipFolder));
  }

  public static FileAttribute<Set<PosixFilePermission>> getFullPermissions() {
    Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
    return PosixFilePermissions.asFileAttribute(permissions);
  }

  private static int getMonthInHumanView(int machineMonth) {
    return machineMonth + 1;
  }
}
