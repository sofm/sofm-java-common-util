package com.github.sofm.common.util.cmd;

import lombok.Data;

@Data
public class CommandResult {

  /*
  // 1: Catchall for general errors
  // 2: Misuse of shell builtins (according to Bash documentation)
  // 126: Command invoked cannot execute
  // 127: "scheduler not found"
  // 128: Invalid argument to exit
  // 128+n: Fatal error signal "n"
  // 130: Script terminated by Control-C
  // 255*: Exit status out of range
  // < 0: Done, but exit status not set
  // > 0: Done, but with error
  // = 0: Done
   */
  private int exitStatus = 99;

  private String output;

  public String toString() {
    return exitStatus + "|" + output;
  }
}
