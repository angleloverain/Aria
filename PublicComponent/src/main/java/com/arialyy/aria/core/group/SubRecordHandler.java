/*
 * Copyright (C) 2016 AriaLyy(https://github.com/AriaLyy/Aria)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.arialyy.aria.core.group;

import com.arialyy.aria.core.TaskRecord;
import com.arialyy.aria.core.ThreadRecord;
import com.arialyy.aria.core.common.RecordHandler;
import com.arialyy.aria.core.common.RecordHelper;
import com.arialyy.aria.core.download.DownloadEntity;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;
import java.util.ArrayList;

/**
 * 子任务记录处理
 */
public class SubRecordHandler extends RecordHandler {
  public SubRecordHandler(AbsTaskWrapper wrapper) {
    super(wrapper);
  }

  @Override public void handlerTaskRecord(TaskRecord record) {
    RecordHelper helper = new RecordHelper(getWrapper(), record);
    helper.handleSingleThreadRecord();
  }

  @Override
  public ThreadRecord createThreadRecord(TaskRecord record, int threadId, long startL, long endL) {
    ThreadRecord tr;
    tr = new ThreadRecord();
    tr.taskKey = record.filePath;
    tr.threadId = threadId;
    tr.startLocation = startL;
    tr.isComplete = false;
    tr.threadType = getEntity().getTaskType();
    tr.endLocation = getFileSize();
    tr.blockLen = getFileSize();
    return tr;
  }

  @Override public TaskRecord createTaskRecord(int threadNum) {
    TaskRecord record = new TaskRecord();
    record.fileName = getEntity().getFileName();
    record.filePath = getEntity().getFilePath();
    record.fileLength = getFileSize();
    record.threadRecords = new ArrayList<>();
    record.threadNum = threadNum;
    record.isBlock = false;
    record.taskType = getEntity().getTaskType();
    record.isGroupRecord = true;
    if (getEntity() instanceof DownloadEntity) {
      record.dGroupHash = ((DownloadEntity) getEntity()).getGroupHash();
    }

    return record;
  }

  @Override public int initTaskThreadNum() {
    return 1;
  }
}
