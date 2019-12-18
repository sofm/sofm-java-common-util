package com.github.sofm.common.util.cmd;

import com.github.sofm.common.util.ExceptionUtil;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@Slf4j
public class UnixCommandUtil {

  public static CommandResult createScript(String scriptPath, String content) {
    try (Writer output = new BufferedWriter(new FileWriter(scriptPath))) {
      output.write(content);
      return ShellCommandUtil.runCommand("chmod u+x " + scriptPath);
    } catch (IOException ex) {
      log.error("(createScript)ex: {}", ExceptionUtil.getFullStackTrace(ex));
      return null;
    }
  }

  public static CommandResult runScript(String scriptPath) {
    return ShellCommandUtil.runCommand(scriptPath);
  }

  public static boolean changeMode(String folderPath) {
    String setModCommand = "chmod -R 775 " + folderPath;
    CommandResult setModResult = ShellCommandUtil.runCommand(setModCommand);
    return setModResult.getExitStatus() == 0;
  }

  public static boolean deleteFolder(String folderPath) {
    String commandTemplate = "rm -rf %FOLDER_PATH%";
    String command = commandTemplate.replace("%FOLDER_PATH%", folderPath);
    CommandResult deleteResult = ShellCommandUtil.runCommand(command);
    return deleteResult.getExitStatus() == 0;
  }

  public static boolean copyFileToRemoteServer(
      String sourcePath, String destinationPath, String server, String username) {
    String commandTemplate = "scp -p %SOURCE_PATH% vt_admin@%SERVER%:%DESTINATION_PATH%";
    String command =
        commandTemplate
            .replace("%SERVER%", server)
            .replace("%SOURCE_PATH%", sourcePath)
            .replace("%DESTINATION_PATH%", destinationPath);
    CommandResult copyResult = ShellCommandUtil.runCommand(command);
    return copyResult.getExitStatus() == 0;
  }

  public static boolean copyFolderToRemoteServer(
      String sourcePath, String destinationPath, String server) {
    String commandTemplate = "scp -r -p %SOURCE_PATH% vt_admin@%SERVER%:%DESTINATION_PATH%";
    String command =
        commandTemplate
            .replace("%SERVER%", server)
            .replace("%SOURCE_PATH%", sourcePath)
            .replace("%DESTINATION_PATH%", destinationPath);
    CommandResult result = ShellCommandUtil.runCommand(command);
    return result.getExitStatus() == 0;
  }
}
