

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for QRTZ_BLOB_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_BLOB_TRIGGERS`;
CREATE TABLE `QRTZ_BLOB_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器名称，对应QRTZ_TRIGGERS表TRIGGER_NAME',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组，对应QRTZ_TRIGGERS表TRIGGER_GROUP',
  `BLOB_DATA` blob NULL COMMENT '存放持久化trigger对象',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `SCHED_NAME`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  CONSTRAINT `QRTZ_BLOB_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '以Blob 类型存储的触发器(用于 Quartz 用户用 JDBC 创建他们自己定制的 Trigger 类型，JobStore 并不知道如何存储实例的时候)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_BLOB_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CALENDARS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CALENDARS`;
CREATE TABLE `QRTZ_CALENDARS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '日历名称',
  `CALENDAR` blob NOT NULL COMMENT '存放持久化calendar对象',
  PRIMARY KEY (`SCHED_NAME`, `CALENDAR_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存放日历信息， quartz可配置一个日历来指定一个时间范围' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_CALENDARS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_CRON_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_CRON_TRIGGERS`;
CREATE TABLE `QRTZ_CRON_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器名称，对应QRTZ_TRIGGERS表TRIGGER_NAME',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组，对应QRTZ_TRIGGERS表TRIGGER_GROUP',
  `CRON_EXPRESSION` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'cron表达式',
  `TIME_ZONE_ID` varchar(80) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '时区',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_CRON_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存放触发器的cron表达式' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_CRON_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('CastleScheduler', 'CASTLE_TRIGGER_1588418600586420225', 'CASTLE_FORTRESS', '0 59 23 * * ?', 'Asia/Shanghai');
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('CastleScheduler', 'CASTLE_TRIGGER_1666981744374755329', 'CASTLE_FORTRESS', '0 0 * * * ?', 'Asia/Shanghai');
INSERT INTO `QRTZ_CRON_TRIGGERS` VALUES ('CastleScheduler', 'CASTLE_TRIGGER_1671429312443138049', 'CASTLE_FORTRESS', '0 0 12 * * ?', 'Asia/Shanghai');

-- ----------------------------
-- Table structure for QRTZ_FIRED_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_FIRED_TRIGGERS`;
CREATE TABLE `QRTZ_FIRED_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `ENTRY_ID` varchar(95) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例id',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器名称，对应QRTZ_TRIGGERS表TRIGGER_NAME',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组，对应QRTZ_TRIGGERS表TRIGGER_GROUP',
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度器实例名称',
  `FIRED_TIME` bigint NOT NULL COMMENT '触发时间',
  `SCHED_TIME` bigint NOT NULL COMMENT '定时器制定的时间',
  `PRIORITY` int NOT NULL COMMENT '优先级',
  `STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '状态',
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'job名称，对应QRTZ_JOB_DETAILS表JOB_NAME',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'job组名称，对应QRTZ_JOB_DETAILS表JOB_GROUP',
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否并发',
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '是否接受恢复执行，默认false',
  PRIMARY KEY (`SCHED_NAME`, `ENTRY_ID`) USING BTREE,
  INDEX `IDX_QRTZ_FT_TRIG_INST_NAME`(`SCHED_NAME` ASC, `INSTANCE_NAME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_INST_JOB_REQ_RCVRY`(`SCHED_NAME` ASC, `INSTANCE_NAME` ASC, `REQUESTS_RECOVERY` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_J_G`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_JG`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_T_G`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_FT_TG`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存放已触发的触发器' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_FIRED_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_JOB_DETAILS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_JOB_DETAILS`;
CREATE TABLE `QRTZ_JOB_DETAILS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'job名称',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'job所属组的名称',
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细的描述信息',
  `JOB_CLASS_NAME` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'job实现类的全限定名,quartz就是根据该字段到classpath找到job类',
  `IS_DURABLE` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否持久化.1:quartz会把job持久化到数据库中',
  `IS_NONCONCURRENT` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否并发执行',
  `IS_UPDATE_DATA` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否更新数据',
  `REQUESTS_RECOVERY` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '是否接受恢复执行，默认false',
  `JOB_DATA` blob NULL COMMENT '一个blob字段，存放持久化job对象',
  PRIMARY KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_J_REQ_RECOVERY`(`SCHED_NAME` ASC, `REQUESTS_RECOVERY` ASC) USING BTREE,
  INDEX `IDX_QRTZ_J_GRP`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存放jobDetail信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_JOB_DETAILS
-- ----------------------------
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('CastleScheduler', 'CASTLE_TASK_1588418600586420225', 'CASTLE_FORTRESS', NULL, 'com.castle.fortress.admin.job.config.ScheduleTask', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400095441534B5F4E414D45740014617474656E64616E63655265636F72645461736B74000B5441534B5F504152414D537400007800);
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('CastleScheduler', 'CASTLE_TASK_1666981744374755329', 'CASTLE_FORTRESS', NULL, 'com.castle.fortress.admin.job.config.ScheduleTask', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400095441534B5F4E414D4574000F4B62426173654C6162656C5461736B74000B5441534B5F504152414D537400007800);
INSERT INTO `QRTZ_JOB_DETAILS` VALUES ('CastleScheduler', 'CASTLE_TASK_1671429312443138049', 'CASTLE_FORTRESS', NULL, 'com.castle.fortress.admin.job.config.ScheduleTask', '0', '1', '0', '0', 0xACED0005737200156F72672E71756172747A2E4A6F62446174614D61709FB083E8BFA9B0CB020000787200266F72672E71756172747A2E7574696C732E537472696E674B65794469727479466C61674D61708208E8C3FBC55D280200015A0013616C6C6F77735472616E7369656E74446174617872001D6F72672E71756172747A2E7574696C732E4469727479466C61674D617013E62EAD28760ACE0200025A000564697274794C00036D617074000F4C6A6176612F7574696C2F4D61703B787001737200116A6176612E7574696C2E486173684D61700507DAC1C31660D103000246000A6C6F6164466163746F724900097468726573686F6C6478703F4000000000000C770800000010000000027400095441534B5F4E414D457400104B624261736544726166746C5461736B74000B5441534B5F504152414D537400007800);

-- ----------------------------
-- Table structure for QRTZ_LOCKS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_LOCKS`;
CREATE TABLE `QRTZ_LOCKS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `LOCK_NAME` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '悲观锁名称',
  PRIMARY KEY (`SCHED_NAME`, `LOCK_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储程序的悲观锁的信息(假如使用了悲观锁)' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_LOCKS
-- ----------------------------
INSERT INTO `QRTZ_LOCKS` VALUES ('CastleScheduler', 'TRIGGER_ACCESS');

-- ----------------------------
-- Table structure for QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_PAUSED_TRIGGER_GRPS`;
CREATE TABLE `QRTZ_PAUSED_TRIGGER_GRPS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组，对应QRTZ_TRIGGERS表TRIGGER_GROUP',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_GROUP`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存放暂停掉的触发器' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_PAUSED_TRIGGER_GRPS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SCHEDULER_STATE
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SCHEDULER_STATE`;
CREATE TABLE `QRTZ_SCHEDULER_STATE`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `INSTANCE_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '实例名称，对应配置文件中org.quartz.scheduler.instanceId配置的名字',
  `LAST_CHECKIN_TIME` bigint NOT NULL COMMENT '上次检查时间',
  `CHECKIN_INTERVAL` bigint NOT NULL COMMENT '检查间隔时间',
  PRIMARY KEY (`SCHED_NAME`, `INSTANCE_NAME`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '存储及群众实例信息，quartz会实时读取该表判断集群中每个实例的当前状态' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_SCHEDULER_STATE
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPLE_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPLE_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPLE_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器名称，对应QRTZ_TRIGGERS表TRIGGER_NAME',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组，对应QRTZ_TRIGGERS表TRIGGER_GROUP',
  `REPEAT_COUNT` bigint NOT NULL COMMENT '重复的次数统计',
  `REPEAT_INTERVAL` bigint NOT NULL COMMENT '重复的间隔时间',
  `TIMES_TRIGGERED` bigint NOT NULL COMMENT '已经触发的次数',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPLE_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '简单触发器的信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_SIMPLE_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_SIMPROP_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_SIMPROP_TRIGGERS`;
CREATE TABLE `QRTZ_SIMPROP_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器名称，对应QRTZ_TRIGGERS表TRIGGER_NAME',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组，对应QRTZ_TRIGGERS表TRIGGER_GROUP',
  `STR_PROP_1` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第一个参数',
  `STR_PROP_2` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第二个参数',
  `STR_PROP_3` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'String类型的trigger的第三个参数',
  `INT_PROP_1` int NULL DEFAULT NULL COMMENT 'int类型的trigger的第一个参数',
  `INT_PROP_2` int NULL DEFAULT NULL COMMENT 'int类型的trigger的第二个参数',
  `LONG_PROP_1` bigint NULL DEFAULT NULL COMMENT 'long类型的trigger的第一个参数',
  `LONG_PROP_2` bigint NULL DEFAULT NULL COMMENT 'long类型的trigger的第二个参数',
  `DEC_PROP_1` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第一个参数',
  `DEC_PROP_2` decimal(13, 4) NULL DEFAULT NULL COMMENT 'decimal类型的trigger的第二个参数',
  `BOOL_PROP_1` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第一个参数',
  `BOOL_PROP_2` varchar(1) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'Boolean类型的trigger的第二个参数',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  CONSTRAINT `QRTZ_SIMPROP_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `QRTZ_TRIGGERS` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器监听器' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_SIMPROP_TRIGGERS
-- ----------------------------

-- ----------------------------
-- Table structure for QRTZ_TRIGGERS
-- ----------------------------
DROP TABLE IF EXISTS `QRTZ_TRIGGERS`;
CREATE TABLE `QRTZ_TRIGGERS`  (
  `SCHED_NAME` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '调度名称',
  `TRIGGER_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器名称',
  `TRIGGER_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器所属组的名称',
  `JOB_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'job名称，对应QRTZ_JOB_DETAILS表JOB_NAME',
  `JOB_GROUP` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'job组名称，对应QRTZ_JOB_DETAILS表JOB_GROUP',
  `DESCRIPTION` varchar(250) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '触发器描述信息',
  `NEXT_FIRE_TIME` bigint NULL DEFAULT NULL COMMENT '下一次触发时间，默认-1，意味不会自动触发',
  `PREV_FIRE_TIME` bigint NULL DEFAULT NULL COMMENT '上一次触发时间(毫秒)',
  `PRIORITY` int NULL DEFAULT NULL COMMENT '优先级',
  `TRIGGER_STATE` varchar(16) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '当前触发器状态，设置为ACQUIRED,如果设置为WAITING,则job不会触发 （ WAITING:等待 PAUSED:暂停ACQUIRED:正常执行 BLOCKED：阻塞 ERROR：错误）',
  `TRIGGER_TYPE` varchar(8) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '触发器类型，使用cron表达式',
  `START_TIME` bigint NOT NULL COMMENT '开始时间',
  `END_TIME` bigint NULL DEFAULT NULL COMMENT '结束时间',
  `CALENDAR_NAME` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '日程表名称，对应QRTZ_CALENDARS的CALENDAR_NAME',
  `MISFIRE_INSTR` smallint NULL DEFAULT NULL COMMENT '措施或者是补偿执行的策略',
  `JOB_DATA` blob NULL COMMENT '一个blob字段，存放持久化的job对象',
  PRIMARY KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) USING BTREE,
  INDEX `IDX_QRTZ_T_J`(`SCHED_NAME` ASC, `JOB_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_JG`(`SCHED_NAME` ASC, `JOB_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_C`(`SCHED_NAME` ASC, `CALENDAR_NAME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_G`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_STATE`(`SCHED_NAME` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_N_STATE`(`SCHED_NAME` ASC, `TRIGGER_NAME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_N_G_STATE`(`SCHED_NAME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NEXT_FIRE_TIME`(`SCHED_NAME` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST`(`SCHED_NAME` ASC, `TRIGGER_STATE` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_MISFIRE`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  INDEX `IDX_QRTZ_T_NFT_ST_MISFIRE_GRP`(`SCHED_NAME` ASC, `MISFIRE_INSTR` ASC, `NEXT_FIRE_TIME` ASC, `TRIGGER_GROUP` ASC, `TRIGGER_STATE` ASC) USING BTREE,
  CONSTRAINT `QRTZ_TRIGGERS_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `QRTZ_JOB_DETAILS` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '触发器的基本信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of QRTZ_TRIGGERS
-- ----------------------------
INSERT INTO `QRTZ_TRIGGERS` VALUES ('CastleScheduler', 'CASTLE_TRIGGER_1588418600586420225', 'CASTLE_FORTRESS', 'CASTLE_TASK_1588418600586420225', 'CASTLE_FORTRESS', NULL, 1669910340000, -1, 5, 'PAUSED', 'CRON', 1669888001000, 0, NULL, 2, '');
INSERT INTO `QRTZ_TRIGGERS` VALUES ('CastleScheduler', 'CASTLE_TRIGGER_1666981744374755329', 'CASTLE_FORTRESS', 'CASTLE_TASK_1666981744374755329', 'CASTLE_FORTRESS', NULL, 1747810800000, 1747807200000, 5, 'WAITING', 'CRON', 1694138021000, 0, NULL, 2, '');
INSERT INTO `QRTZ_TRIGGERS` VALUES ('CastleScheduler', 'CASTLE_TRIGGER_1671429312443138049', 'CASTLE_FORTRESS', 'CASTLE_TASK_1671429312443138049', 'CASTLE_FORTRESS', NULL, 1747886400000, 1747800000000, 5, 'WAITING', 'CRON', 1694138022000, 0, NULL, 2, '');

-- ----------------------------
-- Table structure for castle_api_secret
-- ----------------------------
DROP TABLE IF EXISTS `castle_api_secret`;
CREATE TABLE `castle_api_secret`  (
  `id` bigint NOT NULL COMMENT '主键',
  `cust_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '客户名称',
  `secret_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'secret_id',
  `secret_key` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'secret_key',
  `expired_date` date NULL DEFAULT NULL COMMENT '过期日期',
  `status` int NULL DEFAULT NULL COMMENT '是否启用，系统字典表yes_no',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对外开放接口秘钥' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_api_secret
-- ----------------------------

-- ----------------------------
-- Table structure for castle_approve_form1628226640987361281
-- ----------------------------
DROP TABLE IF EXISTS `castle_approve_form1628226640987361281`;
CREATE TABLE `castle_approve_form1628226640987361281`  (
  `id` bigint NOT NULL,
  `field102` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单行文本',
  `field103` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单行文本',
  `_castle_apply_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交人',
  `_castle_apply_date` datetime NULL DEFAULT NULL COMMENT '提交时间',
  `_castle_apply_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交人所在部门',
  `_castle_apply_dept_parents` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '提交人所在部门上级部门',
  `_castle_apply_post` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '提交人所处职位',
  `_castle_process_instance_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程实例',
  `_castle_process_engine_version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部署版本号',
  `_castle_approve_status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '审批状态',
  `_castle_approve_date` datetime NULL DEFAULT NULL COMMENT '审批时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_approve_form1628226640987361281
-- ----------------------------

-- ----------------------------
-- Table structure for castle_bank
-- ----------------------------
DROP TABLE IF EXISTS `castle_bank`;
CREATE TABLE `castle_bank`  (
  `id` bigint NOT NULL COMMENT '主键',
  `bank_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行名称',
  `bank_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行简码',
  `bank_logo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行logo',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '银行信息' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_bank
-- ----------------------------

-- ----------------------------
-- Table structure for castle_banner
-- ----------------------------
DROP TABLE IF EXISTS `castle_banner`;
CREATE TABLE `castle_banner`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `banner_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片地址',
  `link` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '跳转链接',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT 2 COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'banner图' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_banner
-- ----------------------------
INSERT INTO `castle_banner` VALUES (1493111879691587586, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20220214/1644820580782.jpg', NULL, 3, '测试', 1, -1, '2022-02-14 14:36:27', -1, '2022-02-14 14:47:53', 2);
INSERT INTO `castle_banner` VALUES (1493115443302248450, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20220214/1644821432581.png', NULL, 4, '', 1, -1, '2022-02-14 14:50:37', -1, '2022-02-14 14:56:06', 2);

-- ----------------------------
-- Table structure for castle_config_api
-- ----------------------------
DROP TABLE IF EXISTS `castle_config_api`;
CREATE TABLE `castle_config_api`  (
  `id` bigint NOT NULL COMMENT 'id',
  `group_code` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置分组 00 平台类型 01 api接口 02 自带配置接口',
  `bind_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码 BindApiCodeEnum ',
  `bind_detail` varchar(5000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '配置详情json格式',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '框架绑定api配置管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_config_api
-- ----------------------------
INSERT INTO `castle_config_api` VALUES (1, '00', 'PLAT_CASTLE', '{\"secretId\":\"\",\"secretKey\":\"\"}');
INSERT INTO `castle_config_api` VALUES (2, '00', 'PLAT_ALI', '{\"appId\":\"\",\"secretId\":\"\",\"secretKey\":\"\"}');
INSERT INTO `castle_config_api` VALUES (3, '00', 'PLAT_TENCENT', '{\"appId\":\"\",\"secretId\":\"\",\"secretKey\":\"\",\"cosRegion\":\"\",\"bucketName\":\"\"}');
INSERT INTO `castle_config_api` VALUES (10, '02', 'SELF_CAPTCHA', '{\"captchaAppId\":\"\",\"appSecretKey\":\"**\"}');
INSERT INTO `castle_config_api` VALUES (11, '01', 'API_KEYWORDSEXTRACTION', '{\"options\":[{\"code\":\"PLAT_CASTLE\",\"name\":\"Castle云\"},{\"code\":\"PLAT_TENCENT\",\"name\":\"腾讯云\"}],\"platform\":\"PLAT_TENCENT\"}');
INSERT INTO `castle_config_api` VALUES (12, '01', 'API_AUTOSUMMARIIZATION', '{\"options\":[{\"code\":\"PLAT_CASTLE\",\"name\":\"Castle云\"},{\"code\":\"PLAT_ALI\",\"name\":\"阿里云\"},{\"code\":\"PLAT_TENCENT\",\"name\":\"腾讯云\"}],\"platform\":\"PLAT_TENCENT\"}');
INSERT INTO `castle_config_api` VALUES (13, '01', 'API_TEXTCORRECTION', '{\"options\":[{\"code\":\"PLAT_CASTLE\",\"name\":\"Castle云\"},{\"code\":\"PLAT_ALI\",\"name\":\"阿里云\"},{\"code\":\"PLAT_TENCENT\",\"name\":\"腾讯云\"}],\"platform\":\"PLAT_TENCENT\"}');
INSERT INTO `castle_config_api` VALUES (14, '01', 'API_TEXTAUDIT', '{\"detectType\":\"Politics,Porn,Terrorism,Ads,Illegal,Abuse\",\"options\":[{\"code\":\"PLAT_CASTLE\",\"name\":\"Castle云\"},{\"code\":\"PLAT_TENCENT\",\"name\":\"腾讯云\"}],\"platform\":\"PLAT_TENCENT\"}');
INSERT INTO `castle_config_api` VALUES (15, '01', 'API_ASRWSURL', '{\"options\":[{\"code\":\"PLAT_TENCENT\",\"name\":\"腾讯云\"}],\"platform\":\"PLAT_TENCENT\"}');
INSERT INTO `castle_config_api` VALUES (16, '02', 'SELF_WECOM', '{\"isShow\":\"1\",\"clientId\":\"\",\"agentId\":\"\",\"agentSecret\":\"\",\"syncStatus\":\"1\",\"frontUrl\":\"https://demo.icuapi.com/admin\"}');
INSERT INTO `castle_config_api` VALUES (20, '00', 'WX_LOGIN', '{\"isShow\":\"1\",\"appid\":\"\",\"secret\":\"\",\"url\":\"https://demo.icuapi.com\",\"frontUrl\":\"https://demo.icuapi.com/admin\"}');
INSERT INTO `castle_config_api` VALUES (21, '00', 'QQ_LOGIN', '{\"isShow\":\"1\",\"appid\":\"\",\"secret\":\"\",\"url\":\"\",\"frontUrl\":\"\"}');
INSERT INTO `castle_config_api` VALUES (22, '00', 'DING_LOGIN', '{\"isShow\":\"1\",\"appKey\":\"\",\"appSecret\":\"BSer\",\"agentId\":\"220\",\"loginAppId\":\"dingodl7ztn\",\"loginAppSecret\":\"\",\"url\":\"\",\"frontUrl\":\"https://demo.icuapi.com/admin\",\"backUrl\":\"https://demo.icuapi.com/dingQrCodeLogin\",\"syncStatus\":\"1\"}');
INSERT INTO `castle_config_api` VALUES (30, '00', 'WX_APP_LOGIN', '{\"appid\":\"6ee15\",\"secret\":\"28\"}');
INSERT INTO `castle_config_api` VALUES (31, '00', 'WX_BROWSER_LOGIN', '{\"appid\":\"22222\",\"secret\":\"222222\"}');
INSERT INTO `castle_config_api` VALUES (32, '00', 'COUPON', '{\"url\":\"www.zzz.com\"}');

-- ----------------------------
-- Table structure for castle_config_mail
-- ----------------------------
DROP TABLE IF EXISTS `castle_config_mail`;
CREATE TABLE `castle_config_mail`  (
  `id` bigint NOT NULL COMMENT 'id',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '编码',
  `smtp` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱SMTP地址',
  `port` int UNSIGNED NULL DEFAULT NULL COMMENT '端口号',
  `mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱账号',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱密码',
  `nick_Name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱昵称',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNo  yes启用，no禁用',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_post` bigint NULL DEFAULT NULL COMMENT '创建职位',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_config_mail
-- ----------------------------

-- ----------------------------
-- Table structure for castle_config_oss
-- ----------------------------
DROP TABLE IF EXISTS `castle_config_oss`;
CREATE TABLE `castle_config_oss`  (
  `id` bigint UNSIGNED NOT NULL DEFAULT 0 COMMENT '主键',
  `ft_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件存储编码',
  `platform` tinyint NULL DEFAULT NULL COMMENT '平台类型',
  `pt_config` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台配置',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes生效；no失效',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes删除；no未删除'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件传输配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_config_oss
-- ----------------------------
INSERT INTO `castle_config_oss` VALUES (1638389180039757826, 'mmlocal', 1, '{\"localFilePosition\":\"/data/knowledgebase\",\"localFileUrl\":\"http://nginx/\"}', '本地存储配置，勿删', 1, -1, '2023-03-22 11:56:35', -1, '2023-04-27 14:07:46', 2);

-- ----------------------------
-- Table structure for castle_config_params
-- ----------------------------
DROP TABLE IF EXISTS `castle_config_params`;
CREATE TABLE `castle_config_params`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `param_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数编码',
  `param_value` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数值',
  `param_type` int UNSIGNED NULL DEFAULT 1 COMMENT '类型   1：系统参数   2：非系统参数',
  `param_remark` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数的描述',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes生效；no失效',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_post` bigint NULL DEFAULT NULL COMMENT '创建职位',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1597855361669304322 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统参数表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_config_params
-- ----------------------------
INSERT INTO `castle_config_params` VALUES (1343474867409444865, '001', '002', 1, 'test280', NULL, 1, NULL, NULL, '2020-12-28 16:32:24', -1, '2022-05-07 13:55:36', 1);
INSERT INTO `castle_config_params` VALUES (1365246367871143938, '1', '2', 3, '4', NULL, -1, NULL, NULL, '2021-02-26 18:24:34', -1, '2022-05-07 13:55:36', 1);
INSERT INTO `castle_config_params` VALUES (1365246972312293378, '2', '2', 2, '2', NULL, 1, NULL, NULL, '2021-02-26 18:26:58', -1, '2022-05-07 13:55:36', 1);
INSERT INTO `castle_config_params` VALUES (1414798193423192065, '2', '2', 2, '2', NULL, 1375360470249771009, -1, -1, '2021-07-13 12:05:50', -1, '2022-05-07 13:55:36', 1);
INSERT INTO `castle_config_params` VALUES (1419955578280640514, '2', '2', 2, NULL, 2, 1375360470249771009, -1, -1, '2021-07-27 17:39:26', -1, '2022-05-07 13:55:36', 1);
INSERT INTO `castle_config_params` VALUES (1419955636719878145, '2', '2', 2, NULL, 2, 1375360470249771009, -1, -1, '2021-07-27 17:39:40', -1, '2022-05-07 13:55:36', 1);
INSERT INTO `castle_config_params` VALUES (1419955684883070978, '2', '2', 2, NULL, 2, 1375360470249771009, -1, -1, '2021-07-27 17:39:51', -1, '2022-05-07 13:55:36', 1);
INSERT INTO `castle_config_params` VALUES (1419955784116109314, '3', '3', 3, NULL, 3, 1375360470249771009, -1, -1, '2021-07-27 17:40:15', -1, '2022-05-07 13:55:36', 1);
INSERT INTO `castle_config_params` VALUES (1419955839887769602, '4', '4', 4, NULL, 4, 1375360470249771009, -1, -1, '2021-07-27 17:40:28', -1, '2022-05-07 13:55:32', 1);
INSERT INTO `castle_config_params` VALUES (1419956049217093634, '5', '5', 5, NULL, 2, 1375360470249771009, -1, -1, '2021-07-27 17:41:18', -1, '2022-05-07 13:55:30', 1);
INSERT INTO `castle_config_params` VALUES (1522818912538624001, 'a', 'a', 1, 'a', 1, -1, NULL, NULL, '2022-05-07 14:01:36', NULL, NULL, 2);
INSERT INTO `castle_config_params` VALUES (1597855361669304321, 'logDelete', '-1', 1, '操作日志的删除天数设置,默认30', 1, -1, NULL, NULL, '2022-11-30 15:29:40', -1, '2022-12-01 13:41:09', 2);

-- ----------------------------
-- Table structure for castle_config_sms
-- ----------------------------
DROP TABLE IF EXISTS `castle_config_sms`;
CREATE TABLE `castle_config_sms`  (
  `id` bigint NOT NULL COMMENT 'id',
  `sms_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信编码',
  `platform` tinyint UNSIGNED NOT NULL COMMENT '平台类型 字典sms_platform',
  `sms_config` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信配置',
  `remark` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes生效；no失效',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_post` bigint NULL DEFAULT NULL COMMENT '创建职位',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_config_sms
-- ----------------------------

-- ----------------------------
-- Table structure for castle_config_task
-- ----------------------------
DROP TABLE IF EXISTS `castle_config_task`;
CREATE TABLE `castle_config_task`  (
  `id` bigint NOT NULL COMMENT '主键',
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名称对应代码中component中的name',
  `params` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数',
  `cron_expression` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'cron表达式',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int UNSIGNED NULL DEFAULT NULL COMMENT '任务状态  0：暂停  1：正常',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统任务调度表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_config_task
-- ----------------------------
INSERT INTO `castle_config_task` VALUES (1374655628648902658, 'taskDemo', '', '*/5 * * * * ?', '', 2, -1, '2021-03-24 17:33:36', -1, '2021-03-25 09:32:48', 1);
INSERT INTO `castle_config_task` VALUES (1374897180591673346, 'taskDemo', '', '*/5 * * * * ?', '', 2, -1, '2021-03-25 09:33:27', -1, '2021-03-25 10:44:04', 1);
INSERT INTO `castle_config_task` VALUES (1374920086474448897, 'apiSecretTask', '', '0 0 1 * * ?', '每天凌晨1点执行,将当天过期的api秘钥置为失效,未设置过期时间则永不过期', 2, -1, '2021-03-25 11:04:28', -1, '2021-04-02 14:56:24', 2);
INSERT INTO `castle_config_task` VALUES (1377877539587670017, 'demoTask', '', '*/5 * * * * ?', '测试任务', 2, -1, '2021-04-02 14:56:20', -1, '2021-04-02 15:36:14', 1);
INSERT INTO `castle_config_task` VALUES (1432911171007741954, '测试', '测试', '0 0 0/1 12 * 1/1', '测试', 0, -1, '2021-09-01 11:40:20', -1, '2021-09-01 11:40:46', 2);
INSERT INTO `castle_config_task` VALUES (1588091781857370114, 'releaseUserHolidayBalanceTask', '', '0 59 23 * * ?', '', 0, -1, '2022-11-03 16:52:41', NULL, NULL, 2);
INSERT INTO `castle_config_task` VALUES (1588418600586420225, 'attendanceRecordTask', '', '0 59 23 * * ?', '', 2, -1, '2022-11-04 14:31:20', -1, '2022-11-04 14:35:59', 2);
INSERT INTO `castle_config_task` VALUES (1588420438501044225, 'checkToDayIsHolidays', '', '0 0 12 * * ?', '', 0, -1, '2022-11-04 14:38:39', -1, '2022-12-01 15:07:38', 1);
INSERT INTO `castle_config_task` VALUES (1597873871063662594, 'logDeleteTask', '', '0 0 0 * * ?', '删除日志的任务,每天0点执行', 0, -1, '2022-11-30 16:43:13', -1, '2022-11-30 16:44:41', 2);
INSERT INTO `castle_config_task` VALUES (1598252318230568961, 'checkToDayIsHolidays', '', '0 0 0 1 * ?', '孙工添加备注', 0, -1, '2022-12-01 17:47:01', -1, '2022-12-06 21:10:41', 2);
INSERT INTO `castle_config_task` VALUES (1657213064015327234, 'test01', '12323', '1 * * * * ?', '', 2, -1, '2023-05-13 10:35:59', -1, '2023-05-13 10:36:07', 2);
INSERT INTO `castle_config_task` VALUES (1666622224079278081, 'KbBaseLabelTask', '', '* 0/1 * * * ?', '定时更新es标签', 2, -1, '2023-06-08 09:44:37', -1, '2023-06-08 09:44:41', 2);
INSERT INTO `castle_config_task` VALUES (1671433834916515841, 'KbBaseDraftlTask', '', '0 0 12 * * ?', '', 0, -1, '2023-06-21 16:24:15', NULL, NULL, 2);

-- ----------------------------
-- Table structure for castle_dev_col_config
-- ----------------------------
DROP TABLE IF EXISTS `castle_dev_col_config`;
CREATE TABLE `castle_dev_col_config`  (
  `id` bigint NOT NULL COMMENT '主键',
  `tb_id` bigint NULL DEFAULT NULL COMMENT '表结构id',
  `col_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名',
  `prop_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性名',
  `col_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段类型',
  `prop_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性类型',
  `prop_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段说明',
  `is_list` int NULL DEFAULT NULL COMMENT '是否在列表显示 YesNoEnum',
  `is_copy` int NULL DEFAULT NULL COMMENT '是否复制 YesNoEnum',
  `is_query` int NULL DEFAULT NULL COMMENT '是否在查询条件 YesNoEnum',
  `query_type` int NULL DEFAULT NULL COMMENT '查询条件 DbQueryConditionEnum',
  `is_form` int NULL DEFAULT NULL COMMENT '是否在表单里 YesNoEnum',
  `is_form_require` int NULL DEFAULT NULL COMMENT '是否表单必填 YesNoEnum',
  `validate_type` int NULL DEFAULT NULL COMMENT '自定义校验类型 ValidateTypeEnum',
  `form_type` int NULL DEFAULT NULL COMMENT '表单类型 ViewFormTypeEnum',
  `listdata_type` int NULL DEFAULT NULL COMMENT '集合类型 ListDataTypeEnum 1 字典 2枚举 3 URL 4 JSON',
  `listdata_config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '集合数据配置json对',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成表字段配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_dev_col_config
-- ----------------------------

-- ----------------------------
-- Table structure for castle_dev_db_config
-- ----------------------------
DROP TABLE IF EXISTS `castle_dev_db_config`;
CREATE TABLE `castle_dev_db_config`  (
  `id` bigint NOT NULL COMMENT '主键',
  `db_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据源名称',
  `db_username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库用户名',
  `db_password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库密码',
  `db_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库连接url',
  `db_driver_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据库驱动类',
  `status` int NULL DEFAULT NULL COMMENT '是否启用 ',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成本地配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_dev_db_config
-- ----------------------------
INSERT INTO `castle_dev_db_config` VALUES (1646722035918905345, 'test', 'root', '123456', 'jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&nullCatalogMeansCurrent=true', 'com.mysql.jdbc.Driver', 1, '2023-04-14 11:48:23');

-- ----------------------------
-- Table structure for castle_dev_tb_config
-- ----------------------------
DROP TABLE IF EXISTS `castle_dev_tb_config`;
CREATE TABLE `castle_dev_tb_config`  (
  `id` bigint NOT NULL COMMENT '主键',
  `db_id` bigint NULL DEFAULT NULL COMMENT '所属数据源',
  `tb_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名',
  `back_out_put_dir` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '后端输出根目录',
  `front_out_put_dir` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端输出根目录',
  `module_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模块名',
  `sub_module_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '子模块名',
  `package_base` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '包名',
  `tb_prefix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表前缀',
  `super_entity_class` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实体类对应的父类',
  `tb_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表描述',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `swagger2_flag` int NULL DEFAULT NULL COMMENT '是否启用swagger YesNoEnum',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `view_type` int NULL DEFAULT NULL COMMENT '视图类型1 表格 2 左树右表',
  `tree_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '树数据接口',
  `tree_table_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '树数据对应的表结构',
  `tree_label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '树数据label',
  `tree_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '树数据key',
  `table_col` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表关联字段',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成表结构配置' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_dev_tb_config
-- ----------------------------

-- ----------------------------
-- Table structure for castle_feedback
-- ----------------------------
DROP TABLE IF EXISTS `castle_feedback`;
CREATE TABLE `castle_feedback`  (
  `id` bigint UNSIGNED NOT NULL COMMENT '主键ID',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '反馈内容',
  `img` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '反馈图片,多个逗号隔开',
  `type_id` int NULL DEFAULT NULL COMMENT '反馈类型(字典,闪退/黑屏/卡顿.等等)',
  `member_id` bigint NULL DEFAULT NULL COMMENT '反馈人ID',
  `contact_way` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT 2 COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '意见反馈' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_feedback
-- ----------------------------
INSERT INTO `castle_feedback` VALUES (36, '填写一点意见说明。这里需要填写10鸽子以上', 'http://appyd.lcgjjt.cn/wimg/2022/06/13/8bca7744-b9f3-4610-98a6-8ad6e52f0d81.jpg', 1, 1536229538189541376, '13455656777', NULL, NULL, NULL, NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (37, '这里需要我哥子以上，不然不能发布成功的啊', 'http://appyd.lcgjjt.cn/wimg/2022/06/13/f82e341c-cc48-4563-919f-aec16cf597b6.jpg', 1, 1536229538189541376, '13455656777', NULL, NULL, NULL, NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (38, '这里是问题的详细描述，不能少于10个字', 'http://appyd.lcgjjt.cn/wimg/2022/06/16/26407128-32bc-48a8-8661-0658d6ccf875.png', 1, 1503211080405483520, NULL, NULL, NULL, NULL, NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (39, '这里填写您的意见描述，需要10个字以上。', 'http://appyd.lcgjjt.cn/wimg/2022/06/17/bd67e323-43d6-4f7f-942f-1f4439fee3de.jpg', 1, 1537604498044747776, '18811494406', NULL, NULL, NULL, NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1587348433493114881, '22', '', 1, NULL, '2', NULL, -1, '2022-11-01 15:38:53', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594574345635880961, '测测测测', '啊?是图片字段?', 1, NULL, '联系方式', NULL, 1375358377648590850, '2022-11-21 14:02:42', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594599870060257282, '测试内容', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221121/1669017207572.webp', 3, NULL, '', NULL, 1375358377648590850, '2022-11-21 15:53:30', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594603824743669762, '那就再测试一下', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221121/1669018148764.webp', 2, 1468134832232628225, '15684314381', NULL, NULL, '2022-11-21 16:09:13', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594604463821381633, '测测测测测', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221121/1669018302679.webp', 1, 1468134832232628225, '15684314381', NULL, NULL, '2022-11-21 16:11:45', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594604625792819201, '', '', 1, 1468134832232628225, '15684314381', NULL, NULL, '2022-11-21 16:12:24', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594606691990892545, '测试表单提交置空', '', 1, 1468134832232628225, '15684314381', NULL, NULL, '2022-11-21 16:20:36', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594608319385038850, '测测测测测2', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221121/1669019217627.webp', 1, 1468134832232628225, '15684314381', NULL, NULL, '2022-11-21 16:27:04', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594608612537528322, '测试联系方式', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221121/1669019292035.webp', 1, NULL, '19999999', NULL, NULL, '2022-11-21 16:28:14', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594609256740560898, '测测测测恶策', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221121/1669019447210.webp', 1, NULL, '15684314381', NULL, NULL, '2022-11-21 16:30:48', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594609621385068545, '12312312312312', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221121/1669019533300.webp', 1, 1468134832232628225, '666666666666', NULL, NULL, '2022-11-21 16:32:15', NULL, NULL, 2);
INSERT INTO `castle_feedback` VALUES (1594614463750979585, '测试其他问题', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221121/1669020685640.webp', 4, 1468134832232628225, '6666666', NULL, NULL, '2022-11-21 16:51:29', NULL, NULL, 2);

-- ----------------------------
-- Table structure for castle_flowable_app
-- ----------------------------
DROP TABLE IF EXISTS `castle_flowable_app`;
CREATE TABLE `castle_flowable_app`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '应用标题',
  `flow_id` bigint NULL DEFAULT NULL COMMENT '流程配置id',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1628226991845085186 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '流程应用管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_flowable_app
-- ----------------------------
INSERT INTO `castle_flowable_app` VALUES (1628226991845085185, '11', 1628226733975080962, 1, '2023-02-22 10:55:41');

-- ----------------------------
-- Table structure for castle_flowable_app_auth
-- ----------------------------
DROP TABLE IF EXISTS `castle_flowable_app_auth`;
CREATE TABLE `castle_flowable_app_auth`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `app_id` bigint NULL DEFAULT NULL COMMENT '应用ID',
  `auth_id` bigint NULL DEFAULT NULL COMMENT '权限ID',
  `auth_type` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '权限类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1628235641636347909 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '流程应用权限管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_flowable_app_auth
-- ----------------------------
INSERT INTO `castle_flowable_app_auth` VALUES (1628235641619570690, 1628226991845085185, 1554310820401971202, 'user');
INSERT INTO `castle_flowable_app_auth` VALUES (1628235641636347906, 1628226991845085185, 1597922245961515009, 'user');
INSERT INTO `castle_flowable_app_auth` VALUES (1628235641636347907, 1628226991845085185, 1600047267134369794, 'user');
INSERT INTO `castle_flowable_app_auth` VALUES (1628235641636347908, 1628226991845085185, 1587349375047028738, 'dept');

-- ----------------------------
-- Table structure for castle_flowable_form_config
-- ----------------------------
DROP TABLE IF EXISTS `castle_flowable_form_config`;
CREATE TABLE `castle_flowable_form_config`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单名称',
  `design_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '设计内容,json',
  `tb_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表结构内容',
  `view_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '视图内容',
  `query_condition` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '查询条件',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问链接',
  `save_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据沉淀接口',
  `white_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip白名单',
  `ramark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int NULL DEFAULT NULL COMMENT '访问状态',
  `is_bind` int NULL DEFAULT NULL COMMENT '是否绑定',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_post` bigint NULL DEFAULT NULL COMMENT '创建职位',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  `is_holiday` int NULL DEFAULT NULL COMMENT '是否假勤表单YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1628226640987361282 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '表单配置管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_flowable_form_config
-- ----------------------------
INSERT INTO `castle_flowable_form_config` VALUES (1628226640987361281, '11', '[{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":102,\"renderKey\":\"1021677034461002\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":103,\"renderKey\":\"1031677034461835\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"}]', '[{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":102,\"renderKey\":\"1021677034461002\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":103,\"renderKey\":\"1031677034461835\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"}]', '{\"fields\":[{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":102,\"renderKey\":\"1021677034461002\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":103,\"renderKey\":\"1031677034461835\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"}],\"formRef\":\"elForm\",\"formModel\":\"formData\",\"size\":\"medium\",\"labelPosition\":\"right\",\"labelWidth\":100,\"formRules\":\"rules\",\"gutter\":15,\"disabled\":false,\"span\":24,\"formBtns\":true}', NULL, 'formView?id=1628226640987361281', '/flowable/approves/saveData', NULL, '', NULL, 1, -1, NULL, NULL, '2023-02-22 10:54:17', -1, '2023-02-22 10:54:39', 2, 2);

-- ----------------------------
-- Table structure for castle_flowable_template
-- ----------------------------
DROP TABLE IF EXISTS `castle_flowable_template`;
CREATE TABLE `castle_flowable_template`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `act_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '流程key,不可重复',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '流程标题',
  `content_xml` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '流程内容xml',
  `context_json` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '流程内容json',
  `form_key` bigint NULL DEFAULT NULL COMMENT '绑定表单id',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `deploy_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部署id',
  `engine_version` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部署版本',
  `deploy_time` datetime NULL DEFAULT NULL COMMENT '部署时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1628226733975080963 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '流程模板管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_flowable_template
-- ----------------------------
INSERT INTO `castle_flowable_template` VALUES (1628226733975080962, 'A001', '11', '<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><definitions xmlns=\"http://www.omg.org/spec/BPMN/20100524/MODEL\" xmlns:bpmndi=\"http://www.omg.org/spec/BPMN/20100524/DI\" xmlns:flowable=\"http://flowable.org/bpmn\" xmlns:omgdc=\"http://www.omg.org/spec/DD/20100524/DC\" xmlns:omgdi=\"http://www.omg.org/spec/DD/20100524/DI\" targetNamespace=\"http://flowable.org/bpmn20\"><process id=\"A001\" isExecutable=\"true\" name=\"11\"><extensionElements>\r\n    <flowable:executionListener delegateExpression=\"${fortressHolidayCategaryEndListener}\" event=\"end\"/>\r\n</extensionElements><startEvent id=\"NODE_10000\"/><sequenceFlow id=\"FLOW_10000_67908344830\" sourceRef=\"NODE_10000\" targetRef=\"NODE_67908344830\"/><userTask flowable:assignee=\"${_fortress_assignees_self}\" flowable:fortress_userType=\"self\" id=\"NODE_67908344830\" name=\"审批人\"><extensionElements><flowable:taskListener delegateExpression=\"${fortressUserTaskCompleteListener}\" event=\"complete\"><flowable:field name=\"refuseType\" stringValue=\"ENDED_PRO\"/></flowable:taskListener></extensionElements></userTask><sequenceFlow id=\"FLOW_67908344830_86950344873\" sourceRef=\"NODE_67908344830\" targetRef=\"NODE_86950344873\"/><serviceTask flowable:delegateExpression=\"${fortressRecipientsDelegate}\" id=\"NODE_86950344873\" name=\"抄送人\"><extensionElements><flowable:field name=\"users\"><flowable:string>1600047267134369794</flowable:string></flowable:field></extensionElements></serviceTask><sequenceFlow id=\"FLOW_NODE_86950344873_end_AyarAtiGB65ikNqSy\" sourceRef=\"NODE_86950344873\" targetRef=\"end_AyarAtiGB65ikNqSy\"/><endEvent id=\"end_AyarAtiGB65ikNqSy\"/></process></definitions>', '{\"id\":\"1628226733975080962\",\"baseSetup\":{\"icon\":\"el-icon-s-custom\",\"background\":\"#FF7800\",\"name\":\"11\",\"sign\":false,\"remark\":\"\",\"notify\":{\"types\":[],\"title\":\"\"},\"whoCommit\":[],\"whoEdit\":[],\"whoExport\":[]},\"process\":{\"type\":\"ROOT\",\"name\":\"发起人\",\"id\":\"10000\",\"props\":{\"mode\":\"AND\",\"timeLimit\":{\"type\":\"HOUR\",\"limit\":0,\"event\":{\"type\":\"PASS\",\"loop\":false,\"loopTime\":0}},\"sign\":false,\"userEmpty\":\"TO_PASS\",\"leaderLevel\":1,\"endCondition\":\"TOP\",\"targetObj\":{\"multiple\":false,\"roles\":[],\"objs\":[],\"delivers\":[]},\"refuse\":\"ENDED_PRO\"},\"node\":{\"id\":\"67908344830\",\"pid\":\"10000\",\"name\":\"审批人\",\"type\":\"SP\",\"node\":{\"id\":\"86950344873\",\"pid\":\"67908344830\",\"name\":\"抄送人\",\"type\":\"CS\",\"props\":{\"mode\":\"AND\",\"timeLimit\":{\"type\":\"HOUR\",\"limit\":0,\"event\":{\"type\":\"PASS\",\"loop\":false,\"loopTime\":0}},\"sign\":false,\"userEmpty\":\"TO_PASS\",\"leaderLevel\":1,\"endCondition\":\"TOP\",\"targetObj\":{\"multiple\":false,\"roles\":[],\"objs\":[{\"childFlag\":\"00\",\"name\":\"15684314381\",\"id\":\"1600047267134369794\",\"type\":\"01\",\"parents\":\"潍坊华创信息技术有限公司/技术一部\"}],\"delivers\":[]},\"refuse\":\"ENDED_PRO\"}},\"props\":{\"mode\":\"AND\",\"timeLimit\":{\"type\":\"HOUR\",\"limit\":0,\"event\":{\"type\":\"PASS\",\"loop\":false,\"loopTime\":0}},\"sign\":false,\"userEmpty\":\"TO_PASS\",\"leaderLevel\":1,\"endCondition\":\"TOP\",\"targetObj\":{\"multiple\":false,\"roles\":[],\"objs\":[],\"delivers\":[]},\"refuse\":\"ENDED_PRO\",\"type\":\"SELF\"}}}}', 1628226640987361281, 1, '2023-02-22 10:54:39', '5041fa8d-b25c-11ed-868c-0250f2000002', '1', '2023-02-22 10:55:08');

-- ----------------------------
-- Table structure for castle_form1480817395594092545
-- ----------------------------
DROP TABLE IF EXISTS `castle_form1480817395594092545`;
CREATE TABLE `castle_form1480817395594092545`  (
  `id` bigint NOT NULL,
  `field101` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `field118` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `field119` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '留言内容',
  `field138` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '计数器',
  `field142` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单选框组',
  `field143` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '多选框组',
  `field141` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '级联选择',
  `field140` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下拉选择',
  `field139` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '编辑器',
  `field137` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `field135` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单行文本',
  `field136` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '多行文本',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_form1480817395594092545
-- ----------------------------
INSERT INTO `castle_form1480817395594092545` VALUES (1507170712402313217, '777', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `castle_form1480817395594092545` VALUES (1507174797570523137, '999', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `castle_form1480817395594092545` VALUES (1507181291389108226, '123456', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for castle_form1536657052313628674
-- ----------------------------
DROP TABLE IF EXISTS `castle_form1536657052313628674`;
CREATE TABLE `castle_form1536657052313628674`  (
  `id` bigint NOT NULL,
  `field101` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `field103` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `field104` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '反馈内容',
  `field105` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省市区',
  `field105Name` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省市区名称描述',
  `phone_sms` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_form1536657052313628674
-- ----------------------------
INSERT INTO `castle_form1536657052313628674` VALUES (1536657519166668801, '孙工', '18811494406', '我是反馈的内容！', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `castle_form1536657052313628674` VALUES (1536660356739153921, '1', '1', '1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `castle_form1536657052313628674` VALUES (1536660445687758850, '1', '1', '1', NULL, NULL, NULL, NULL, NULL);
INSERT INTO `castle_form1536657052313628674` VALUES (1541329107501744130, '孙工', '18811494406', '111111', '110000,110100,110105', '北京市,北京市,朝阳区', '18811494406', -1, '2022-06-27 15:54:30');

-- ----------------------------
-- Table structure for castle_form1574304638308208642
-- ----------------------------
DROP TABLE IF EXISTS `castle_form1574304638308208642`;
CREATE TABLE `castle_form1574304638308208642`  (
  `id` bigint NOT NULL,
  `field101` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请假类型',
  `field102` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开始日期',
  `field106` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '结束日期',
  `field107` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '请假原因',
  `field108` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '说明图片',
  `field109` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '说明文件',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_form1574304638308208642
-- ----------------------------
INSERT INTO `castle_form1574304638308208642` VALUES (1574657952380112898, '1', '2022-09-01', '2022-09-02', '123', NULL, NULL);
INSERT INTO `castle_form1574304638308208642` VALUES (1574658388768083970, '1', '2022-09-02', '2022-09-03', '123456', NULL, NULL);
INSERT INTO `castle_form1574304638308208642` VALUES (1574658547409244161, '2', '2022-09-05', '2022-09-06', '33333', NULL, NULL);

-- ----------------------------
-- Table structure for castle_form1611245185698656257
-- ----------------------------
DROP TABLE IF EXISTS `castle_form1611245185698656257`;
CREATE TABLE `castle_form1611245185698656257`  (
  `id` bigint NOT NULL,
  `field101` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '您的姓名',
  `field102` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '联系方式',
  `field103` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '留言内容',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_form1611245185698656257
-- ----------------------------
INSERT INTO `castle_form1611245185698656257` VALUES (1611247708819976193, '1', '2', '3');

-- ----------------------------
-- Table structure for castle_form_config
-- ----------------------------
DROP TABLE IF EXISTS `castle_form_config`;
CREATE TABLE `castle_form_config`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `tb_id` bigint NULL DEFAULT NULL COMMENT '表id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单名称',
  `design_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '设计内容,json',
  `tb_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '表结构内容',
  `view_content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '视图内容',
  `query_condition` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '查询条件',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '访问链接',
  `save_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '数据沉淀接口',
  `white_list` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip白名单',
  `ramark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int NULL DEFAULT NULL COMMENT '访问状态',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_post` bigint NULL DEFAULT NULL COMMENT '创建职位',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1611245185698656258 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '表单配置管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_form_config
-- ----------------------------
INSERT INTO `castle_form_config` VALUES (1456107748417253377, NULL, 'form001', '[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011637805061677\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"多行文本\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":112,\"renderKey\":\"1121637826454411\"},\"type\":\"textarea\",\"placeholder\":\"请输入多行文本\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field112\"},{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":116,\"renderKey\":\"1161637913320737\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field116\"},{\"__config__\":{\"label\":\"时间选择\",\"tag\":\"el-time-picker\",\"tagIcon\":\"time\",\"defaultValue\":null,\"span\":23,\"showLabel\":true,\"layout\":\"colFormItem\",\"labelWidth\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/time-picker\",\"formId\":119,\"renderKey\":\"1191637915763001\"},\"placeholder\":\"请选择时间选择\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"picker-options\":{\"selectableRange\":\"00:00:00-23:59:59\"},\"format\":\"HH:mm:ss\",\"value-format\":\"HH:mm:ss\",\"__vModel__\":\"field119\"}]', '[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011637805061677\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"多行文本\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":112,\"renderKey\":\"1121637826454411\"},\"type\":\"textarea\",\"placeholder\":\"请输入多行文本\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field112\"},{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":116,\"renderKey\":\"1161637913320737\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field116\"},{\"__config__\":{\"label\":\"时间选择\",\"tag\":\"el-time-picker\",\"tagIcon\":\"time\",\"defaultValue\":null,\"span\":23,\"showLabel\":true,\"layout\":\"colFormItem\",\"labelWidth\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/time-picker\",\"formId\":119,\"renderKey\":\"1191637915763001\"},\"placeholder\":\"请选择时间选择\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"picker-options\":{\"selectableRange\":\"00:00:00-23:59:59\"},\"format\":\"HH:mm:ss\",\"value-format\":\"HH:mm:ss\",\"__vModel__\":\"field119\"}]', '{\"fields\":[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011637805061677\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"多行文本\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":112,\"renderKey\":\"1121637826454411\"},\"type\":\"textarea\",\"placeholder\":\"请输入多行文本\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field112\"},{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":116,\"renderKey\":\"1161637913320737\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field116\"},{\"__config__\":{\"label\":\"时间选择\",\"tag\":\"el-time-picker\",\"tagIcon\":\"time\",\"defaultValue\":null,\"span\":23,\"showLabel\":true,\"layout\":\"colFormItem\",\"labelWidth\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/time-picker\",\"formId\":119,\"renderKey\":\"1191637915763001\"},\"placeholder\":\"请选择时间选择\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"picker-options\":{\"selectableRange\":\"00:00:00-23:59:59\"},\"format\":\"HH:mm:ss\",\"value-format\":\"HH:mm:ss\",\"__vModel__\":\"field119\"}],\"formRef\":\"elForm\",\"formModel\":\"formData\",\"size\":\"medium\",\"labelPosition\":\"right\",\"labelWidth\":100,\"formRules\":\"rules\",\"gutter\":15,\"disabled\":false,\"span\":24,\"formBtns\":true}', '[{\"checked\":true,\"model\":\"field101\",\"title\":\"姓名\",\"tag\":\"el-input\",\"queryType\":\"1\"},{\"checked\":true,\"model\":\"field112\",\"title\":\"多行文本\",\"tag\":\"el-input\",\"queryType\":\"3\"},{\"checked\":null,\"model\":\"field116\",\"title\":\"单行文本\",\"tag\":\"el-input\",\"queryType\":\"\"},{\"checked\":null,\"model\":\"field119\",\"title\":\"时间选择\",\"tag\":\"el-time-picker\",\"queryType\":\"\"}]', '345', '123', NULL, '测试0012', 2, -1, NULL, NULL, '2021-11-04 11:55:15', -1, '2022-06-14 18:29:08', 1);
INSERT INTO `castle_form_config` VALUES (1465946165971996673, NULL, 'form003', '[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011638347826298\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"地址\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":102,\"renderKey\":\"1021638347827299\"},\"type\":\"textarea\",\"placeholder\":\"请输入地址\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"电话\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":103,\"renderKey\":\"1031638347828883\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入电话\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"},{\"__config__\":{\"label\":\"下拉选择\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"select\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":104,\"renderKey\":\"1041638347830918\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"placeholder\":\"请选择下拉选择\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field104\"},{\"__config__\":{\"label\":\"计数器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"el-input-number\",\"tagIcon\":\"number\",\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input-number\",\"formId\":105,\"renderKey\":\"1051638347833837\"},\"placeholder\":\"计数器\",\"step\":1,\"step-strictly\":false,\"controls-position\":\"\",\"disabled\":false,\"__vModel__\":\"field105\"},{\"__config__\":{\"label\":\"单选框组\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-radio-group\",\"tagIcon\":\"radio\",\"changeTag\":true,\"layout\":\"colFormItem\",\"span\":24,\"optionType\":\"default\",\"regList\":[],\"required\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/radio\",\"formId\":106,\"renderKey\":\"1061638347835989\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field106\"}]', '[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011638347826298\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"地址\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":102,\"renderKey\":\"1021638347827299\"},\"type\":\"textarea\",\"placeholder\":\"请输入地址\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"电话\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":103,\"renderKey\":\"1031638347828883\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入电话\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"},{\"__config__\":{\"label\":\"下拉选择\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"select\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":104,\"renderKey\":\"1041638347830918\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"placeholder\":\"请选择下拉选择\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field104\"},{\"__config__\":{\"label\":\"计数器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"el-input-number\",\"tagIcon\":\"number\",\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input-number\",\"formId\":105,\"renderKey\":\"1051638347833837\"},\"placeholder\":\"计数器\",\"step\":1,\"step-strictly\":false,\"controls-position\":\"\",\"disabled\":false,\"__vModel__\":\"field105\"},{\"__config__\":{\"label\":\"单选框组\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-radio-group\",\"tagIcon\":\"radio\",\"changeTag\":true,\"layout\":\"colFormItem\",\"span\":24,\"optionType\":\"default\",\"regList\":[],\"required\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/radio\",\"formId\":106,\"renderKey\":\"1061638347835989\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field106\"}]', '{\"fields\":[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011638347826298\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"地址\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":102,\"renderKey\":\"1021638347827299\"},\"type\":\"textarea\",\"placeholder\":\"请输入地址\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"电话\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":103,\"renderKey\":\"1031638347828883\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入电话\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"},{\"__config__\":{\"label\":\"下拉选择\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"select\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":104,\"renderKey\":\"1041638347830918\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"placeholder\":\"请选择下拉选择\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field104\"},{\"__config__\":{\"label\":\"计数器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"el-input-number\",\"tagIcon\":\"number\",\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input-number\",\"formId\":105,\"renderKey\":\"1051638347833837\"},\"placeholder\":\"计数器\",\"step\":1,\"step-strictly\":false,\"controls-position\":\"\",\"disabled\":false,\"__vModel__\":\"field105\"},{\"__config__\":{\"label\":\"单选框组\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-radio-group\",\"tagIcon\":\"radio\",\"changeTag\":true,\"layout\":\"colFormItem\",\"span\":24,\"optionType\":\"default\",\"regList\":[],\"required\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/radio\",\"formId\":106,\"renderKey\":\"1061638347835989\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field106\"}],\"formRef\":\"elForm\",\"formModel\":\"formData\",\"size\":\"medium\",\"labelPosition\":\"right\",\"labelWidth\":100,\"formRules\":\"rules\",\"gutter\":15,\"disabled\":false,\"span\":24,\"formBtns\":true}', '[{\"checked\":true,\"model\":\"field101\",\"title\":\"姓名\",\"tag\":\"el-input\",\"queryType\":\"7\"},{\"checked\":true,\"model\":\"field102\",\"title\":\"地址\",\"tag\":\"el-input\",\"queryType\":\"2\"},{\"checked\":true,\"model\":\"field103\",\"title\":\"电话\",\"tag\":\"el-input\",\"queryType\":\"1\"},{\"checked\":true,\"model\":\"field104\",\"title\":\"下拉选择\",\"tag\":\"el-select\",\"queryType\":\"\"},{\"checked\":null,\"model\":\"field105\",\"title\":\"计数器\",\"tag\":\"el-input-number\",\"queryType\":\"\"},{\"checked\":null,\"model\":\"field106\",\"title\":\"单选框组\",\"tag\":\"el-radio-group\",\"queryType\":\"\"}]', NULL, NULL, NULL, '表单003', 1, -1, NULL, NULL, '2021-12-01 15:29:37', -1, '2022-06-14 18:29:10', 1);
INSERT INTO `castle_form_config` VALUES (1468053068625629186, NULL, 'form007', '[{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"defaultValue\":\"root\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011638846298055\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"多行文本\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":102,\"renderKey\":\"1021638846299574\"},\"type\":\"textarea\",\"placeholder\":\"请输入多行文本\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"密码\",\"showLabel\":true,\"labelWidth\":null,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"password\",\"defaultValue\":\"root@123\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":103,\"renderKey\":\"1031638846300758\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入密码\",\"show-password\":true,\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"},{\"__config__\":{\"label\":\"计数器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"el-input-number\",\"tagIcon\":\"number\",\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input-number\",\"formId\":104,\"renderKey\":\"1041638846302726\"},\"placeholder\":\"计数器\",\"step\":1,\"step-strictly\":false,\"controls-position\":\"\",\"disabled\":false,\"__vModel__\":\"field104\"},{\"__config__\":{\"label\":\"下拉选择\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"select\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":106,\"renderKey\":\"1061638846306713\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"placeholder\":\"请选择下拉选择\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field106\"},{\"__config__\":{\"label\":\"单选框组\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-radio-group\",\"tagIcon\":\"radio\",\"changeTag\":true,\"layout\":\"colFormItem\",\"span\":24,\"optionType\":\"default\",\"regList\":[],\"required\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/radio\",\"formId\":108,\"renderKey\":\"1081638846313986\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field108\"},{\"__config__\":{\"label\":\"日期范围\",\"tag\":\"el-date-picker\",\"tagIcon\":\"date-range\",\"defaultValue\":null,\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"required\":true,\"layout\":\"colFormItem\",\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":121,\"renderKey\":\"1211638849583599\"},\"style\":{\"width\":\"100%\"},\"type\":\"daterange\",\"range-separator\":\"至\",\"start-placeholder\":\"开始日期\",\"end-placeholder\":\"结束日期\",\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field121\"},{\"__config__\":{\"label\":\"颜色选择\",\"tag\":\"el-color-picker\",\"tagIcon\":\"color\",\"span\":24,\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/color-picker\",\"formId\":117,\"renderKey\":\"1171638846342844\"},\"show-alpha\":false,\"color-format\":\"\",\"disabled\":false,\"size\":\"medium\",\"__vModel__\":\"field117\"},{\"__config__\":{\"label\":\"评分\",\"tag\":\"el-rate\",\"tagIcon\":\"rate\",\"defaultValue\":0,\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/rate\",\"formId\":116,\"renderKey\":\"1161638846341242\"},\"style\":{},\"max\":5,\"allow-half\":false,\"show-text\":false,\"show-score\":false,\"disabled\":false,\"__vModel__\":\"field116\"},{\"__config__\":{\"label\":\"时间范围\",\"tag\":\"el-time-picker\",\"tagIcon\":\"time-range\",\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"defaultValue\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/time-picker\",\"formId\":120,\"renderKey\":\"1201638849517407\"},\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"is-range\":true,\"range-separator\":\"至\",\"start-placeholder\":\"开始时间\",\"end-placeholder\":\"结束时间\",\"format\":\"HH:mm:ss\",\"value-format\":\"HH:mm:ss\",\"__vModel__\":\"field120\"},{\"__config__\":{\"label\":\"日期选择\",\"tag\":\"el-date-picker\",\"tagIcon\":\"date\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":114,\"renderKey\":\"1141638846336572\"},\"placeholder\":\"请选择日期选择\",\"type\":\"date\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field114\"},{\"__config__\":{\"label\":\"开关\",\"tag\":\"el-switch\",\"tagIcon\":\"switch\",\"defaultValue\":false,\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/switch\",\"formId\":110,\"renderKey\":\"1101638846325262\"},\"style\":{},\"disabled\":false,\"active-text\":\"\",\"inactive-text\":\"\",\"active-color\":null,\"inactive-color\":null,\"active-value\":true,\"inactive-value\":false,\"__vModel__\":\"field110\"},{\"__config__\":{\"label\":\"滑块\",\"tag\":\"el-slider\",\"tagIcon\":\"slider\",\"defaultValue\":0,\"span\":24,\"showLabel\":true,\"layout\":\"colFormItem\",\"labelWidth\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/slider\",\"formId\":111,\"renderKey\":\"1111638846327547\"},\"disabled\":false,\"min\":0,\"max\":100,\"step\":1,\"show-stops\":false,\"range\":false,\"__vModel__\":\"field111\"},{\"__config__\":{\"label\":\"时间选择\",\"tag\":\"el-time-picker\",\"tagIcon\":\"time\",\"defaultValue\":\"11:22:44\",\"span\":24,\"showLabel\":true,\"layout\":\"colFormItem\",\"labelWidth\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/time-picker\",\"formId\":112,\"renderKey\":\"1121638846330066\"},\"placeholder\":\"请选择时间选择\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"picker-options\":{\"selectableRange\":\"00:00:00-23:59:59\"},\"format\":\"HH:mm:ss\",\"value-format\":\"HH:mm:ss\",\"__vModel__\":\"field112\"}]', '[{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"defaultValue\":\"root\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011638846298055\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"多行文本\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":102,\"renderKey\":\"1021638846299574\"},\"type\":\"textarea\",\"placeholder\":\"请输入多行文本\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"密码\",\"showLabel\":true,\"labelWidth\":null,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"password\",\"defaultValue\":\"root@123\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":103,\"renderKey\":\"1031638846300758\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入密码\",\"show-password\":true,\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"},{\"__config__\":{\"label\":\"计数器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"el-input-number\",\"tagIcon\":\"number\",\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input-number\",\"formId\":104,\"renderKey\":\"1041638846302726\"},\"placeholder\":\"计数器\",\"step\":1,\"step-strictly\":false,\"controls-position\":\"\",\"disabled\":false,\"__vModel__\":\"field104\"},{\"__config__\":{\"label\":\"下拉选择\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"select\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":106,\"renderKey\":\"1061638846306713\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"placeholder\":\"请选择下拉选择\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field106\"},{\"__config__\":{\"label\":\"单选框组\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-radio-group\",\"tagIcon\":\"radio\",\"changeTag\":true,\"layout\":\"colFormItem\",\"span\":24,\"optionType\":\"default\",\"regList\":[],\"required\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/radio\",\"formId\":108,\"renderKey\":\"1081638846313986\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field108\"},{\"__config__\":{\"label\":\"日期范围\",\"tag\":\"el-date-picker\",\"tagIcon\":\"date-range\",\"defaultValue\":null,\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"required\":true,\"layout\":\"colFormItem\",\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":121,\"renderKey\":\"1211638849583599\"},\"style\":{\"width\":\"100%\"},\"type\":\"daterange\",\"range-separator\":\"至\",\"start-placeholder\":\"开始日期\",\"end-placeholder\":\"结束日期\",\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field121\"},{\"__config__\":{\"label\":\"颜色选择\",\"tag\":\"el-color-picker\",\"tagIcon\":\"color\",\"span\":24,\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/color-picker\",\"formId\":117,\"renderKey\":\"1171638846342844\"},\"show-alpha\":false,\"color-format\":\"\",\"disabled\":false,\"size\":\"medium\",\"__vModel__\":\"field117\"},{\"__config__\":{\"label\":\"评分\",\"tag\":\"el-rate\",\"tagIcon\":\"rate\",\"defaultValue\":0,\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/rate\",\"formId\":116,\"renderKey\":\"1161638846341242\"},\"style\":{},\"max\":5,\"allow-half\":false,\"show-text\":false,\"show-score\":false,\"disabled\":false,\"__vModel__\":\"field116\"},{\"__config__\":{\"label\":\"时间范围\",\"tag\":\"el-time-picker\",\"tagIcon\":\"time-range\",\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"defaultValue\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/time-picker\",\"formId\":120,\"renderKey\":\"1201638849517407\"},\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"is-range\":true,\"range-separator\":\"至\",\"start-placeholder\":\"开始时间\",\"end-placeholder\":\"结束时间\",\"format\":\"HH:mm:ss\",\"value-format\":\"HH:mm:ss\",\"__vModel__\":\"field120\"},{\"__config__\":{\"label\":\"日期选择\",\"tag\":\"el-date-picker\",\"tagIcon\":\"date\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":114,\"renderKey\":\"1141638846336572\"},\"placeholder\":\"请选择日期选择\",\"type\":\"date\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field114\"},{\"__config__\":{\"label\":\"开关\",\"tag\":\"el-switch\",\"tagIcon\":\"switch\",\"defaultValue\":false,\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/switch\",\"formId\":110,\"renderKey\":\"1101638846325262\"},\"style\":{},\"disabled\":false,\"active-text\":\"\",\"inactive-text\":\"\",\"active-color\":null,\"inactive-color\":null,\"active-value\":true,\"inactive-value\":false,\"__vModel__\":\"field110\"},{\"__config__\":{\"label\":\"滑块\",\"tag\":\"el-slider\",\"tagIcon\":\"slider\",\"defaultValue\":0,\"span\":24,\"showLabel\":true,\"layout\":\"colFormItem\",\"labelWidth\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/slider\",\"formId\":111,\"renderKey\":\"1111638846327547\"},\"disabled\":false,\"min\":0,\"max\":100,\"step\":1,\"show-stops\":false,\"range\":false,\"__vModel__\":\"field111\"},{\"__config__\":{\"label\":\"时间选择\",\"tag\":\"el-time-picker\",\"tagIcon\":\"time\",\"defaultValue\":\"11:22:44\",\"span\":24,\"showLabel\":true,\"layout\":\"colFormItem\",\"labelWidth\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/time-picker\",\"formId\":112,\"renderKey\":\"1121638846330066\"},\"placeholder\":\"请选择时间选择\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"picker-options\":{\"selectableRange\":\"00:00:00-23:59:59\"},\"format\":\"HH:mm:ss\",\"value-format\":\"HH:mm:ss\",\"__vModel__\":\"field112\"}]', '{\"fields\":[{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"defaultValue\":\"root\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011638846298055\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"多行文本\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":102,\"renderKey\":\"1021638846299574\"},\"type\":\"textarea\",\"placeholder\":\"请输入多行文本\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"密码\",\"showLabel\":true,\"labelWidth\":null,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"password\",\"defaultValue\":\"root@123\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":103,\"renderKey\":\"1031638846300758\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入密码\",\"show-password\":true,\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"},{\"__config__\":{\"label\":\"计数器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"el-input-number\",\"tagIcon\":\"number\",\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input-number\",\"formId\":104,\"renderKey\":\"1041638846302726\"},\"placeholder\":\"计数器\",\"step\":1,\"step-strictly\":false,\"controls-position\":\"\",\"disabled\":false,\"__vModel__\":\"field104\"},{\"__config__\":{\"label\":\"下拉选择\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"select\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":106,\"renderKey\":\"1061638846306713\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"placeholder\":\"请选择下拉选择\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field106\"},{\"__config__\":{\"label\":\"单选框组\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-radio-group\",\"tagIcon\":\"radio\",\"changeTag\":true,\"layout\":\"colFormItem\",\"span\":24,\"optionType\":\"default\",\"regList\":[],\"required\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/radio\",\"formId\":108,\"renderKey\":\"1081638846313986\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field108\"},{\"__config__\":{\"label\":\"日期范围\",\"tag\":\"el-date-picker\",\"tagIcon\":\"date-range\",\"defaultValue\":null,\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"required\":true,\"layout\":\"colFormItem\",\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":121,\"renderKey\":\"1211638849583599\"},\"style\":{\"width\":\"100%\"},\"type\":\"daterange\",\"range-separator\":\"至\",\"start-placeholder\":\"开始日期\",\"end-placeholder\":\"结束日期\",\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field121\"},{\"__config__\":{\"label\":\"颜色选择\",\"tag\":\"el-color-picker\",\"tagIcon\":\"color\",\"span\":24,\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/color-picker\",\"formId\":117,\"renderKey\":\"1171638846342844\"},\"show-alpha\":false,\"color-format\":\"\",\"disabled\":false,\"size\":\"medium\",\"__vModel__\":\"field117\"},{\"__config__\":{\"label\":\"评分\",\"tag\":\"el-rate\",\"tagIcon\":\"rate\",\"defaultValue\":0,\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/rate\",\"formId\":116,\"renderKey\":\"1161638846341242\"},\"style\":{},\"max\":5,\"allow-half\":false,\"show-text\":false,\"show-score\":false,\"disabled\":false,\"__vModel__\":\"field116\"},{\"__config__\":{\"label\":\"时间范围\",\"tag\":\"el-time-picker\",\"tagIcon\":\"time-range\",\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"defaultValue\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/time-picker\",\"formId\":120,\"renderKey\":\"1201638849517407\"},\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"is-range\":true,\"range-separator\":\"至\",\"start-placeholder\":\"开始时间\",\"end-placeholder\":\"结束时间\",\"format\":\"HH:mm:ss\",\"value-format\":\"HH:mm:ss\",\"__vModel__\":\"field120\"},{\"__config__\":{\"label\":\"日期选择\",\"tag\":\"el-date-picker\",\"tagIcon\":\"date\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":114,\"renderKey\":\"1141638846336572\"},\"placeholder\":\"请选择日期选择\",\"type\":\"date\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field114\"},{\"__config__\":{\"label\":\"开关\",\"tag\":\"el-switch\",\"tagIcon\":\"switch\",\"defaultValue\":false,\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/switch\",\"formId\":110,\"renderKey\":\"1101638846325262\"},\"style\":{},\"disabled\":false,\"active-text\":\"\",\"inactive-text\":\"\",\"active-color\":null,\"inactive-color\":null,\"active-value\":true,\"inactive-value\":false,\"__vModel__\":\"field110\"},{\"__config__\":{\"label\":\"滑块\",\"tag\":\"el-slider\",\"tagIcon\":\"slider\",\"defaultValue\":0,\"span\":24,\"showLabel\":true,\"layout\":\"colFormItem\",\"labelWidth\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/slider\",\"formId\":111,\"renderKey\":\"1111638846327547\"},\"disabled\":false,\"min\":0,\"max\":100,\"step\":1,\"show-stops\":false,\"range\":false,\"__vModel__\":\"field111\"},{\"__config__\":{\"label\":\"时间选择\",\"tag\":\"el-time-picker\",\"tagIcon\":\"time\",\"defaultValue\":\"11:22:44\",\"span\":24,\"showLabel\":true,\"layout\":\"colFormItem\",\"labelWidth\":null,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/time-picker\",\"formId\":112,\"renderKey\":\"1121638846330066\"},\"placeholder\":\"请选择时间选择\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"picker-options\":{\"selectableRange\":\"00:00:00-23:59:59\"},\"format\":\"HH:mm:ss\",\"value-format\":\"HH:mm:ss\",\"__vModel__\":\"field112\"}],\"formRef\":\"elForm\",\"formModel\":\"formData\",\"size\":\"medium\",\"labelPosition\":\"right\",\"labelWidth\":100,\"formRules\":\"rules\",\"gutter\":15,\"disabled\":false,\"span\":24,\"formBtns\":true}', NULL, '/formView?id=1468053068625629186', '/formManage/saveData', NULL, '测试组件类型', 1, -1, NULL, NULL, '2021-12-07 11:01:41', -1, '2022-06-14 18:29:12', 1);
INSERT INTO `castle_form_config` VALUES (1472861631123804161, NULL, 'test', '[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":\"\",\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"defaultValue\":\"\",\"required\":true,\"layout\":\"colFormItem\",\"span\":22,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011639992580207\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"name\"},{\"__config__\":{\"label\":\"手机号\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"defaultValue\":\"\",\"required\":true,\"layout\":\"colFormItem\",\"span\":22,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":106,\"renderKey\":\"1061639992734328\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入手机号\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":true,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"phone\"},{\"__config__\":{\"label\":\"密码\",\"showLabel\":true,\"labelWidth\":null,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"password\",\"defaultValue\":\"\",\"layout\":\"colFormItem\",\"span\":22,\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":103,\"renderKey\":\"1031639992687296\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入密码\",\"show-password\":true,\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"password\"}]', '[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":\"\",\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"defaultValue\":\"\",\"required\":true,\"layout\":\"colFormItem\",\"span\":22,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011639992580207\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"name\"},{\"__config__\":{\"label\":\"手机号\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"defaultValue\":\"\",\"required\":true,\"layout\":\"colFormItem\",\"span\":22,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":106,\"renderKey\":\"1061639992734328\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入手机号\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":true,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"phone\"},{\"__config__\":{\"label\":\"密码\",\"showLabel\":true,\"labelWidth\":null,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"password\",\"defaultValue\":\"\",\"layout\":\"colFormItem\",\"span\":22,\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":103,\"renderKey\":\"1031639992687296\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入密码\",\"show-password\":true,\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"password\"}]', '{\"fields\":[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":\"\",\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"defaultValue\":\"\",\"required\":true,\"layout\":\"colFormItem\",\"span\":22,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011639992580207\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"name\"},{\"__config__\":{\"label\":\"手机号\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"defaultValue\":\"\",\"required\":true,\"layout\":\"colFormItem\",\"span\":22,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":106,\"renderKey\":\"1061639992734328\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入手机号\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":true,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"phone\"},{\"__config__\":{\"label\":\"密码\",\"showLabel\":true,\"labelWidth\":null,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"password\",\"defaultValue\":\"\",\"layout\":\"colFormItem\",\"span\":22,\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":103,\"renderKey\":\"1031639992687296\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入密码\",\"show-password\":true,\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"password\"}],\"formRef\":\"会员信息\",\"formModel\":\"formData\",\"size\":\"medium\",\"labelPosition\":\"right\",\"labelWidth\":100,\"formRules\":\"rules\",\"gutter\":15,\"disabled\":false,\"span\":22,\"formBtns\":true}', NULL, '/formView?id=1472861631123804161', '/formManage/saveData', NULL, '测试', 1, -1, NULL, NULL, '2021-12-20 17:29:12', -1, '2022-06-14 18:29:13', 1);
INSERT INTO `castle_form_config` VALUES (1480817395594092545, NULL, 'form002', '[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":118,\"renderKey\":\"1181655200525833\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field118\"},{\"__config__\":{\"label\":\"计数器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"el-input-number\",\"tagIcon\":\"el-icon-c-scale-to-original\",\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input-number\",\"formId\":138,\"renderKey\":\"1381655202285780\"},\"placeholder\":\"计数器\",\"step\":1,\"step-strictly\":false,\"controls-position\":\"\",\"disabled\":false,\"__vModel__\":\"field138\"},{\"__config__\":{\"label\":\"单选框组\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-radio-group\",\"tagIcon\":\"el-icon-remove-outline\",\"changeTag\":true,\"layout\":\"colFormItem\",\"span\":24,\"optionType\":\"default\",\"regList\":[],\"required\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/radio\",\"formId\":142,\"renderKey\":\"1421655202297194\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field142\"},{\"__config__\":{\"label\":\"多选框组\",\"tag\":\"el-checkbox-group\",\"tagIcon\":\"el-icon-circle-plus-outline\",\"defaultValue\":[],\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"optionType\":\"default\",\"required\":true,\"regList\":[],\"changeTag\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/checkbox\",\"formId\":143,\"renderKey\":\"1431655202299158\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field143\"},{\"__config__\":{\"label\":\"级联选择\",\"url\":\"https://www.fastmock.site/mock/f8d7a54fb1e60561e2f720d5a810009d/fg/cascaderList\",\"method\":\"get\",\"dataPath\":\"list\",\"dataConsumer\":\"options\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-cascader\",\"tagIcon\":\"el-icon-connection\",\"layout\":\"colFormItem\",\"defaultValue\":[],\"dataType\":\"static\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/cascader\",\"formId\":141,\"renderKey\":\"1411655202294763\"},\"options\":[{\"id\":1,\"value\":\"1\",\"label\":\"选项1\",\"children\":[{\"id\":2,\"value\":\"2\",\"label\":\"选项1-1\"}]}],\"placeholder\":\"请选择级联选择\",\"style\":{\"width\":\"100%\"},\"props\":{\"props\":{\"multiple\":false,\"label\":\"label\",\"value\":\"value\",\"children\":\"children\"}},\"show-all-levels\":true,\"disabled\":false,\"clearable\":true,\"filterable\":false,\"separator\":\"/\",\"__vModel__\":\"field141\"},{\"__config__\":{\"label\":\"下拉选择\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"el-icon-arrow-down\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":140,\"renderKey\":\"1401655202290455\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"placeholder\":\"请选择下拉选择\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field140\"},{\"__config__\":{\"label\":\"手机号\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":101,\"renderKey\":\"1011642059066475\",\"defaultValue\":\"root\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入手机号\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"编辑器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"fortress-editor\",\"tagIcon\":\"el-icon-edit-outline\",\"defaultValue\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"http://tinymce.ax-z.cn\",\"formId\":139,\"renderKey\":\"1391655202287862\"},\"placeholder\":\"请输入编辑器\",\"height\":300,\"branding\":false,\"__vModel__\":\"field139\"},{\"__config__\":{\"label\":\"密码\",\"showLabel\":true,\"labelWidth\":null,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-view\",\"defaultValue\":\"root@123\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":137,\"renderKey\":\"1371655202276321\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入密码\",\"show-password\":true,\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field137\"},{\"__config__\":{\"label\":\"留言内容\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":119,\"renderKey\":\"1191655200537609\"},\"type\":\"textarea\",\"placeholder\":\"请输入留言内容\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field119\"},{\"__config__\":{\"label\":\"分隔符\",\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-divider\",\"tagIcon\":\"el-icon-minus\",\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/divider\",\"regList\":[],\"position\":\"left\",\"formId\":133,\"renderKey\":\"1331655202266329\"},\"style\":{\"width\":\"100%\"},\"__vModel__\":\"field133\"},{\"__config__\":{\"label\":\"纯文本\",\"showLabel\":true,\"changeTag\":true,\"tag\":\"span\",\"tagIcon\":\"el-icon-document\",\"defaultValue\":\"显示内容\",\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/divider\",\"regList\":[],\"itemClass\":\"font-size: 14px;color: #606266;\",\"formId\":134,\"renderKey\":\"1341655202269188\"},\"style\":{\"width\":\"100%\"},\"__vModel__\":\"field134\"},{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":135,\"renderKey\":\"1351655202271125\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field135\"},{\"__config__\":{\"label\":\"多行文本\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":136,\"renderKey\":\"1361655202273112\"},\"type\":\"textarea\",\"placeholder\":\"请输入多行文本\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field136\"}]', '[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":118,\"renderKey\":\"1181655200525833\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field118\"},{\"__config__\":{\"label\":\"计数器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"el-input-number\",\"tagIcon\":\"el-icon-c-scale-to-original\",\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input-number\",\"formId\":138,\"renderKey\":\"1381655202285780\"},\"placeholder\":\"计数器\",\"step\":1,\"step-strictly\":false,\"controls-position\":\"\",\"disabled\":false,\"__vModel__\":\"field138\"},{\"__config__\":{\"label\":\"单选框组\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-radio-group\",\"tagIcon\":\"el-icon-remove-outline\",\"changeTag\":true,\"layout\":\"colFormItem\",\"span\":24,\"optionType\":\"default\",\"regList\":[],\"required\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/radio\",\"formId\":142,\"renderKey\":\"1421655202297194\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field142\"},{\"__config__\":{\"label\":\"多选框组\",\"tag\":\"el-checkbox-group\",\"tagIcon\":\"el-icon-circle-plus-outline\",\"defaultValue\":[],\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"optionType\":\"default\",\"required\":true,\"regList\":[],\"changeTag\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/checkbox\",\"formId\":143,\"renderKey\":\"1431655202299158\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field143\"},{\"__config__\":{\"label\":\"级联选择\",\"url\":\"https://www.fastmock.site/mock/f8d7a54fb1e60561e2f720d5a810009d/fg/cascaderList\",\"method\":\"get\",\"dataPath\":\"list\",\"dataConsumer\":\"options\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-cascader\",\"tagIcon\":\"el-icon-connection\",\"layout\":\"colFormItem\",\"defaultValue\":[],\"dataType\":\"static\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/cascader\",\"formId\":141,\"renderKey\":\"1411655202294763\"},\"options\":[{\"id\":1,\"value\":\"1\",\"label\":\"选项1\",\"children\":[{\"id\":2,\"value\":\"2\",\"label\":\"选项1-1\"}]}],\"placeholder\":\"请选择级联选择\",\"style\":{\"width\":\"100%\"},\"props\":{\"props\":{\"multiple\":false,\"label\":\"label\",\"value\":\"value\",\"children\":\"children\"}},\"show-all-levels\":true,\"disabled\":false,\"clearable\":true,\"filterable\":false,\"separator\":\"/\",\"__vModel__\":\"field141\"},{\"__config__\":{\"label\":\"下拉选择\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"el-icon-arrow-down\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":140,\"renderKey\":\"1401655202290455\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"placeholder\":\"请选择下拉选择\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field140\"},{\"__config__\":{\"label\":\"手机号\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":101,\"renderKey\":\"1011642059066475\",\"defaultValue\":\"root\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入手机号\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"编辑器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"fortress-editor\",\"tagIcon\":\"el-icon-edit-outline\",\"defaultValue\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"http://tinymce.ax-z.cn\",\"formId\":139,\"renderKey\":\"1391655202287862\"},\"placeholder\":\"请输入编辑器\",\"height\":300,\"branding\":false,\"__vModel__\":\"field139\"},{\"__config__\":{\"label\":\"密码\",\"showLabel\":true,\"labelWidth\":null,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-view\",\"defaultValue\":\"root@123\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":137,\"renderKey\":\"1371655202276321\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入密码\",\"show-password\":true,\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field137\"},{\"__config__\":{\"label\":\"留言内容\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":119,\"renderKey\":\"1191655200537609\"},\"type\":\"textarea\",\"placeholder\":\"请输入留言内容\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field119\"},{\"__config__\":{\"label\":\"分隔符\",\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-divider\",\"tagIcon\":\"el-icon-minus\",\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/divider\",\"regList\":[],\"position\":\"left\",\"formId\":133,\"renderKey\":\"1331655202266329\"},\"style\":{\"width\":\"100%\"},\"__vModel__\":\"field133\"},{\"__config__\":{\"label\":\"纯文本\",\"showLabel\":true,\"changeTag\":true,\"tag\":\"span\",\"tagIcon\":\"el-icon-document\",\"defaultValue\":\"显示内容\",\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/divider\",\"regList\":[],\"itemClass\":\"font-size: 14px;color: #606266;\",\"formId\":134,\"renderKey\":\"1341655202269188\"},\"style\":{\"width\":\"100%\"},\"__vModel__\":\"field134\"},{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":135,\"renderKey\":\"1351655202271125\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field135\"},{\"__config__\":{\"label\":\"多行文本\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":136,\"renderKey\":\"1361655202273112\"},\"type\":\"textarea\",\"placeholder\":\"请输入多行文本\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field136\"}]', '{\"fields\":[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":118,\"renderKey\":\"1181655200525833\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field118\"},{\"__config__\":{\"label\":\"计数器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"el-input-number\",\"tagIcon\":\"el-icon-c-scale-to-original\",\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input-number\",\"formId\":138,\"renderKey\":\"1381655202285780\"},\"placeholder\":\"计数器\",\"step\":1,\"step-strictly\":false,\"controls-position\":\"\",\"disabled\":false,\"__vModel__\":\"field138\"},{\"__config__\":{\"label\":\"单选框组\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-radio-group\",\"tagIcon\":\"el-icon-remove-outline\",\"changeTag\":true,\"layout\":\"colFormItem\",\"span\":24,\"optionType\":\"default\",\"regList\":[],\"required\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/radio\",\"formId\":142,\"renderKey\":\"1421655202297194\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field142\"},{\"__config__\":{\"label\":\"多选框组\",\"tag\":\"el-checkbox-group\",\"tagIcon\":\"el-icon-circle-plus-outline\",\"defaultValue\":[],\"span\":24,\"showLabel\":true,\"labelWidth\":null,\"layout\":\"colFormItem\",\"optionType\":\"default\",\"required\":true,\"regList\":[],\"changeTag\":true,\"border\":false,\"document\":\"https://element.eleme.cn/#/zh-CN/component/checkbox\",\"formId\":143,\"renderKey\":\"1431655202299158\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"style\":{},\"size\":\"medium\",\"disabled\":false,\"__vModel__\":\"field143\"},{\"__config__\":{\"label\":\"级联选择\",\"url\":\"https://www.fastmock.site/mock/f8d7a54fb1e60561e2f720d5a810009d/fg/cascaderList\",\"method\":\"get\",\"dataPath\":\"list\",\"dataConsumer\":\"options\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-cascader\",\"tagIcon\":\"el-icon-connection\",\"layout\":\"colFormItem\",\"defaultValue\":[],\"dataType\":\"static\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/cascader\",\"formId\":141,\"renderKey\":\"1411655202294763\"},\"options\":[{\"id\":1,\"value\":\"1\",\"label\":\"选项1\",\"children\":[{\"id\":2,\"value\":\"2\",\"label\":\"选项1-1\"}]}],\"placeholder\":\"请选择级联选择\",\"style\":{\"width\":\"100%\"},\"props\":{\"props\":{\"multiple\":false,\"label\":\"label\",\"value\":\"value\",\"children\":\"children\"}},\"show-all-levels\":true,\"disabled\":false,\"clearable\":true,\"filterable\":false,\"separator\":\"/\",\"__vModel__\":\"field141\"},{\"__config__\":{\"label\":\"下拉选择\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"el-icon-arrow-down\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":140,\"renderKey\":\"1401655202290455\"},\"__slot__\":{\"options\":[{\"label\":\"选项一\",\"value\":1},{\"label\":\"选项二\",\"value\":2}]},\"placeholder\":\"请选择下拉选择\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field140\"},{\"__config__\":{\"label\":\"手机号\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":101,\"renderKey\":\"1011642059066475\",\"defaultValue\":\"root\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入手机号\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"编辑器\",\"showLabel\":true,\"changeTag\":true,\"labelWidth\":null,\"tag\":\"fortress-editor\",\"tagIcon\":\"el-icon-edit-outline\",\"defaultValue\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"document\":\"http://tinymce.ax-z.cn\",\"formId\":139,\"renderKey\":\"1391655202287862\"},\"placeholder\":\"请输入编辑器\",\"height\":300,\"branding\":false,\"__vModel__\":\"field139\"},{\"__config__\":{\"label\":\"密码\",\"showLabel\":true,\"labelWidth\":null,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-view\",\"defaultValue\":\"root@123\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":137,\"renderKey\":\"1371655202276321\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入密码\",\"show-password\":true,\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field137\"},{\"__config__\":{\"label\":\"留言内容\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"textarea\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":119,\"renderKey\":\"1191655200537609\"},\"type\":\"textarea\",\"placeholder\":\"请输入留言内容\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field119\"},{\"__config__\":{\"label\":\"分隔符\",\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-divider\",\"tagIcon\":\"el-icon-minus\",\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/divider\",\"regList\":[],\"position\":\"left\",\"formId\":133,\"renderKey\":\"1331655202266329\"},\"style\":{\"width\":\"100%\"},\"__vModel__\":\"field133\"},{\"__config__\":{\"label\":\"纯文本\",\"showLabel\":true,\"changeTag\":true,\"tag\":\"span\",\"tagIcon\":\"el-icon-document\",\"defaultValue\":\"显示内容\",\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/divider\",\"regList\":[],\"itemClass\":\"font-size: 14px;color: #606266;\",\"formId\":134,\"renderKey\":\"1341655202269188\"},\"style\":{\"width\":\"100%\"},\"__vModel__\":\"field134\"},{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":135,\"renderKey\":\"1351655202271125\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field135\"},{\"__config__\":{\"label\":\"多行文本\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":136,\"renderKey\":\"1361655202273112\"},\"type\":\"textarea\",\"placeholder\":\"请输入多行文本\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field136\"}],\"formRef\":\"elForm\",\"formModel\":\"formData\",\"size\":\"medium\",\"labelPosition\":\"right\",\"labelWidth\":100,\"formRules\":\"rules\",\"gutter\":15,\"disabled\":false,\"span\":24,\"formBtns\":true}', NULL, '/formView?id=1480817395594092545', '/formManage/saveData', NULL, '开发测试', 1, -1, NULL, NULL, '2022-01-11 16:22:34', -1, '2022-06-14 18:25:09', 2);
INSERT INTO `castle_form_config` VALUES (1481157606236663809, NULL, '152', '[{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011641970469562\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"}]', '[{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011641970469562\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"}]', '{\"fields\":[{\"__config__\":{\"label\":\"单行文本\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"input\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"formId\":101,\"renderKey\":\"1011641970469562\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入单行文本\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"}],\"formRef\":\"elForm\",\"formModel\":\"formData\",\"size\":\"medium\",\"labelPosition\":\"right\",\"labelWidth\":100,\"formRules\":\"rules\",\"gutter\":15,\"disabled\":false,\"span\":24,\"formBtns\":true}', NULL, 'formView?id=1481157606236663809', 'formManage/saveData', NULL, '', 2, -1, NULL, NULL, '2022-01-12 14:54:27', -1, '2022-01-12 14:54:53', 1);
INSERT INTO `castle_form_config` VALUES (1536657052313628674, NULL, '用户信息收集表', '[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":101,\"renderKey\":\"1011655202569816\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"手机号\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":103,\"renderKey\":\"1031655202582139\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入手机号\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"},{\"__config__\":{\"label\":\"反馈内容\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":false,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":104,\"renderKey\":\"1041655202587521\"},\"type\":\"textarea\",\"placeholder\":\"请输入反馈内容\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field104\"},{\"__config__\":{\"label\":\"省市区\",\"tag\":\"fortress-region\",\"tagIcon\":\"el-icon-location-information\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"required\":true,\"span\":24,\"showTip\":false,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"fileSize\":2,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"formId\":105,\"renderKey\":\"1051656316381845\"},\"__slot__\":{\"list-type\":true},\"placeholder\":\"请选择省市区\",\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"disabled\":false,\"accept\":\"\",\"name\":\"file\",\"auto-upload\":true,\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"field105\"},{\"__config__\":{\"label\":\"手机号\",\"tag\":\"phone_sms\",\"tagIcon\":\"el-icon-mobile-phone\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"span\":24,\"buttonText\":\"点击上传\",\"regList\":[{\"pattern\":\"/^[1][3-9][0-9]{9}$/\",\"message\":\"请输入正确的手机号\"}],\"changeTag\":true,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"isUnique\":false,\"intervalSecond\":60,\"validityMinute\":5,\"formId\":106,\"renderKey\":\"1061656316387057\"},\"__slot__\":{\"list-type\":true},\"placeholder\":\"请填写正确的手机号\",\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"phone_sms\"},{\"__config__\":{\"label\":\"创建人\",\"tag\":\"create_user\",\"tagIcon\":\"el-icon-user\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"span\":24,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"formId\":107,\"renderKey\":\"1071656316395848\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"list-type\":\"text\",\"__vModel__\":\"create_user\"},{\"__config__\":{\"label\":\"创建时间\",\"tag\":\"create_time\",\"tagIcon\":\"el-icon-date\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"span\":24,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"formId\":108,\"renderKey\":\"1081656316397549\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"list-type\":\"text\",\"__vModel__\":\"create_time\"}]', '[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":101,\"renderKey\":\"1011655202569816\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"手机号\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":103,\"renderKey\":\"1031655202582139\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入手机号\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"},{\"__config__\":{\"label\":\"反馈内容\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":false,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":104,\"renderKey\":\"1041655202587521\"},\"type\":\"textarea\",\"placeholder\":\"请输入反馈内容\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field104\"},{\"__config__\":{\"label\":\"省市区\",\"tag\":\"fortress-region\",\"tagIcon\":\"el-icon-location-information\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"required\":true,\"span\":24,\"showTip\":false,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"fileSize\":2,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"formId\":105,\"renderKey\":\"1051656316381845\"},\"__slot__\":{\"list-type\":true},\"placeholder\":\"请选择省市区\",\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"disabled\":false,\"accept\":\"\",\"name\":\"file\",\"auto-upload\":true,\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"field105\"},{\"__config__\":{\"label\":\"手机号\",\"tag\":\"phone_sms\",\"tagIcon\":\"el-icon-mobile-phone\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"span\":24,\"buttonText\":\"点击上传\",\"regList\":[{\"pattern\":\"/^[1][3-9][0-9]{9}$/\",\"message\":\"请输入正确的手机号\"}],\"changeTag\":true,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"isUnique\":false,\"intervalSecond\":60,\"validityMinute\":5,\"formId\":106,\"renderKey\":\"1061656316387057\"},\"__slot__\":{\"list-type\":true},\"placeholder\":\"请填写正确的手机号\",\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"phone_sms\"},{\"__config__\":{\"label\":\"创建人\",\"tag\":\"create_user\",\"tagIcon\":\"el-icon-user\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"span\":24,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"formId\":107,\"renderKey\":\"1071656316395848\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"list-type\":\"text\",\"__vModel__\":\"create_user\"},{\"__config__\":{\"label\":\"创建时间\",\"tag\":\"create_time\",\"tagIcon\":\"el-icon-date\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"span\":24,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"formId\":108,\"renderKey\":\"1081656316397549\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"list-type\":\"text\",\"__vModel__\":\"create_time\"}]', '{\"fields\":[{\"__config__\":{\"label\":\"姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":101,\"renderKey\":\"1011655202569816\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"手机号\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":103,\"renderKey\":\"1031655202582139\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入手机号\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"},{\"__config__\":{\"label\":\"反馈内容\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":false,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":104,\"renderKey\":\"1041655202587521\"},\"type\":\"textarea\",\"placeholder\":\"请输入反馈内容\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field104\"},{\"__config__\":{\"label\":\"省市区\",\"tag\":\"fortress-region\",\"tagIcon\":\"el-icon-location-information\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"required\":true,\"span\":24,\"showTip\":false,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"fileSize\":2,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"formId\":105,\"renderKey\":\"1051656316381845\"},\"__slot__\":{\"list-type\":true},\"placeholder\":\"请选择省市区\",\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"disabled\":false,\"accept\":\"\",\"name\":\"file\",\"auto-upload\":true,\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"field105\"},{\"__config__\":{\"label\":\"手机号\",\"tag\":\"phone_sms\",\"tagIcon\":\"el-icon-mobile-phone\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"span\":24,\"buttonText\":\"点击上传\",\"regList\":[{\"pattern\":\"/^[1][3-9][0-9]{9}$/\",\"message\":\"请输入正确的手机号\"}],\"changeTag\":true,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"isUnique\":false,\"intervalSecond\":60,\"validityMinute\":5,\"formId\":106,\"renderKey\":\"1061656316387057\"},\"__slot__\":{\"list-type\":true},\"placeholder\":\"请填写正确的手机号\",\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"phone_sms\"},{\"__config__\":{\"label\":\"创建人\",\"tag\":\"create_user\",\"tagIcon\":\"el-icon-user\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"span\":24,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"formId\":107,\"renderKey\":\"1071656316395848\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"list-type\":\"text\",\"__vModel__\":\"create_user\"},{\"__config__\":{\"label\":\"创建时间\",\"tag\":\"create_time\",\"tagIcon\":\"el-icon-date\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"span\":24,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"formId\":108,\"renderKey\":\"1081656316397549\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"list-type\":\"text\",\"__vModel__\":\"create_time\"}],\"formRef\":\"elForm\",\"formModel\":\"formData\",\"size\":\"medium\",\"labelPosition\":\"right\",\"labelWidth\":100,\"formRules\":\"rules\",\"gutter\":15,\"disabled\":false,\"span\":24,\"formBtns\":true}', NULL, 'formView?id=1536657052313628674', 'formManage/saveData', '*.*.*.*', '', 1, -1, NULL, NULL, '2022-06-14 18:29:26', -1, '2022-06-27 15:53:22', 2);
INSERT INTO `castle_form_config` VALUES (1574304638308208642, NULL, '请假', '[{\"__config__\":{\"label\":\"请假类型\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"el-icon-arrow-down\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":101,\"renderKey\":\"1011664178465747\",\"defaultValue\":1},\"__slot__\":{\"options\":[{\"label\":\"事假\",\"value\":1},{\"label\":\"病假\",\"value\":2}]},\"placeholder\":\"请选择请假类型\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"开始日期\",\"tag\":\"el-date-picker\",\"tagIcon\":\"el-icon-date\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":102,\"renderKey\":\"1021664178513805\"},\"placeholder\":\"请选择开始日期\",\"type\":\"date\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"结束日期\",\"tag\":\"el-date-picker\",\"tagIcon\":\"el-icon-date\",\"defaultValue\":\"2022-10-11\",\"showLabel\":true,\"labelWidth\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":106,\"renderKey\":\"1061664178664345\"},\"placeholder\":\"请选择结束日期\",\"type\":\"date\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field106\"},{\"__config__\":{\"label\":\"请假原因\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":107,\"renderKey\":\"1071664178680169\",\"defaultValue\":\"123\"},\"type\":\"textarea\",\"placeholder\":\"请输入请假原因\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field107\"},{\"__config__\":{\"label\":\"说明图片\",\"tag\":\"image-upload\",\"tagIcon\":\"el-icon-picture-outline\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"required\":false,\"span\":24,\"showTip\":false,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"fileSize\":2,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"limit\":5,\"formId\":108,\"renderKey\":\"1081664178700703\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"disabled\":false,\"accept\":\"\",\"name\":\"file\",\"auto-upload\":true,\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"field108\"},{\"__config__\":{\"label\":\"说明文件\",\"tag\":\"file-upload\",\"tagIcon\":\"el-icon-paperclip\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"required\":false,\"span\":24,\"showTip\":false,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"fileSize\":2,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"limit\":5,\"formId\":109,\"renderKey\":\"1091664178710553\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"disabled\":false,\"accept\":\"\",\"name\":\"file\",\"auto-upload\":true,\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"field109\"}]', '[{\"__config__\":{\"label\":\"请假类型\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"el-icon-arrow-down\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":101,\"renderKey\":\"1011664178465747\",\"defaultValue\":1},\"__slot__\":{\"options\":[{\"label\":\"事假\",\"value\":1},{\"label\":\"病假\",\"value\":2}]},\"placeholder\":\"请选择请假类型\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"开始日期\",\"tag\":\"el-date-picker\",\"tagIcon\":\"el-icon-date\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":102,\"renderKey\":\"1021664178513805\"},\"placeholder\":\"请选择开始日期\",\"type\":\"date\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"结束日期\",\"tag\":\"el-date-picker\",\"tagIcon\":\"el-icon-date\",\"defaultValue\":\"2022-10-11\",\"showLabel\":true,\"labelWidth\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":106,\"renderKey\":\"1061664178664345\"},\"placeholder\":\"请选择结束日期\",\"type\":\"date\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field106\"},{\"__config__\":{\"label\":\"请假原因\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":107,\"renderKey\":\"1071664178680169\",\"defaultValue\":\"123\"},\"type\":\"textarea\",\"placeholder\":\"请输入请假原因\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field107\"},{\"__config__\":{\"label\":\"说明图片\",\"tag\":\"image-upload\",\"tagIcon\":\"el-icon-picture-outline\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"required\":false,\"span\":24,\"showTip\":false,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"fileSize\":2,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"limit\":5,\"formId\":108,\"renderKey\":\"1081664178700703\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"disabled\":false,\"accept\":\"\",\"name\":\"file\",\"auto-upload\":true,\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"field108\"},{\"__config__\":{\"label\":\"说明文件\",\"tag\":\"file-upload\",\"tagIcon\":\"el-icon-paperclip\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"required\":false,\"span\":24,\"showTip\":false,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"fileSize\":2,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"limit\":5,\"formId\":109,\"renderKey\":\"1091664178710553\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"disabled\":false,\"accept\":\"\",\"name\":\"file\",\"auto-upload\":true,\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"field109\"}]', '{\"fields\":[{\"__config__\":{\"label\":\"请假类型\",\"showLabel\":true,\"labelWidth\":null,\"tag\":\"el-select\",\"tagIcon\":\"el-icon-arrow-down\",\"layout\":\"colFormItem\",\"span\":24,\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/select\",\"formId\":101,\"renderKey\":\"1011664178465747\",\"defaultValue\":1},\"__slot__\":{\"options\":[{\"label\":\"事假\",\"value\":1},{\"label\":\"病假\",\"value\":2}]},\"placeholder\":\"请选择请假类型\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"disabled\":false,\"filterable\":false,\"multiple\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"开始日期\",\"tag\":\"el-date-picker\",\"tagIcon\":\"el-icon-date\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":102,\"renderKey\":\"1021664178513805\"},\"placeholder\":\"请选择开始日期\",\"type\":\"date\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"结束日期\",\"tag\":\"el-date-picker\",\"tagIcon\":\"el-icon-date\",\"defaultValue\":\"2022-10-11\",\"showLabel\":true,\"labelWidth\":null,\"span\":24,\"layout\":\"colFormItem\",\"required\":true,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/date-picker\",\"formId\":106,\"renderKey\":\"1061664178664345\"},\"placeholder\":\"请选择结束日期\",\"type\":\"date\",\"style\":{\"width\":\"100%\"},\"disabled\":false,\"clearable\":true,\"format\":\"yyyy-MM-dd\",\"value-format\":\"yyyy-MM-dd\",\"readonly\":false,\"__vModel__\":\"field106\"},{\"__config__\":{\"label\":\"请假原因\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":107,\"renderKey\":\"1071664178680169\",\"defaultValue\":\"123\"},\"type\":\"textarea\",\"placeholder\":\"请输入请假原因\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field107\"},{\"__config__\":{\"label\":\"说明图片\",\"tag\":\"image-upload\",\"tagIcon\":\"el-icon-picture-outline\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"required\":false,\"span\":24,\"showTip\":false,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"fileSize\":2,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"limit\":5,\"formId\":108,\"renderKey\":\"1081664178700703\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"disabled\":false,\"accept\":\"\",\"name\":\"file\",\"auto-upload\":true,\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"field108\"},{\"__config__\":{\"label\":\"说明文件\",\"tag\":\"file-upload\",\"tagIcon\":\"el-icon-paperclip\",\"layout\":\"colFormItem\",\"defaultValue\":null,\"showLabel\":true,\"labelWidth\":null,\"required\":false,\"span\":24,\"showTip\":false,\"buttonText\":\"点击上传\",\"regList\":[],\"changeTag\":true,\"fileSize\":2,\"sizeUnit\":\"MB\",\"document\":\"https://element.eleme.cn/#/zh-CN/component/upload\",\"limit\":5,\"formId\":109,\"renderKey\":\"1091664178710553\"},\"__slot__\":{\"list-type\":true},\"action\":\"https://jsonplaceholder.typicode.com/posts/\",\"disabled\":false,\"accept\":\"\",\"name\":\"file\",\"auto-upload\":true,\"list-type\":\"text\",\"multiple\":false,\"__vModel__\":\"field109\"}],\"formRef\":\"elForm\",\"formModel\":\"formData\",\"size\":\"medium\",\"labelPosition\":\"right\",\"labelWidth\":100,\"formRules\":\"rules\",\"gutter\":15,\"disabled\":false,\"span\":24,\"formBtns\":true}', '[{\"checked\":true,\"model\":\"field101\",\"title\":\"请假类型\",\"tag\":\"el-select\",\"queryType\":\"1\"},{\"checked\":true,\"model\":\"field102\",\"title\":\"开始日期\",\"tag\":\"el-date-picker\",\"queryType\":\"\"},{\"model\":\"field106\",\"title\":\"结束日期\",\"tag\":\"el-date-picker\",\"queryType\":\"\"},{\"checked\":true,\"model\":\"field107\",\"title\":\"请假原因\",\"tag\":\"el-input\",\"queryType\":\"\"},{\"checked\":false,\"model\":\"field108\",\"title\":\"说明图片\",\"tag\":\"image-upload\",\"queryType\":\"\"},{\"checked\":false,\"model\":\"field109\",\"title\":\"说明文件\",\"tag\":\"file-upload\",\"queryType\":\"\"}]', 'formView?id=1574304638308208642', 'formManage/saveData/abc', NULL, '请假流程', 1, -1, NULL, NULL, '2022-09-26 15:47:30', -1, '2022-10-10 15:05:28', 2);
INSERT INTO `castle_form_config` VALUES (1574669849351520258, NULL, '请假', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', 2, -1, NULL, NULL, '2022-09-27 15:58:43', -1, '2022-09-27 15:58:46', 1);
INSERT INTO `castle_form_config` VALUES (1611245185698656257, NULL, '网站留言收集', '[{\"__config__\":{\"label\":\"您的姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":101,\"renderKey\":\"1011672985772112\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"输入单行文本请您的姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"联系方式\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":102,\"renderKey\":\"1021672985785416\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入联系方式\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"留言内容\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":103,\"renderKey\":\"1031672985791007\"},\"type\":\"textarea\",\"placeholder\":\"请输入留言内容\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"}]', '[{\"__config__\":{\"label\":\"您的姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":101,\"renderKey\":\"1011672985772112\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"输入单行文本请您的姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"联系方式\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":102,\"renderKey\":\"1021672985785416\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入联系方式\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"留言内容\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":103,\"renderKey\":\"1031672985791007\"},\"type\":\"textarea\",\"placeholder\":\"请输入留言内容\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"}]', '{\"fields\":[{\"__config__\":{\"label\":\"您的姓名\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":101,\"renderKey\":\"1011672985772112\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"输入单行文本请您的姓名\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field101\"},{\"__config__\":{\"label\":\"联系方式\",\"labelWidth\":null,\"showLabel\":true,\"changeTag\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-edit\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"regList\":[],\"regList2\":{\"id\":null,\"pattern\":null,\"message\":\"\"},\"formId\":102,\"renderKey\":\"1021672985785416\"},\"__slot__\":{\"prepend\":\"\",\"append\":\"\"},\"placeholder\":\"请输入联系方式\",\"style\":{\"width\":\"100%\"},\"clearable\":true,\"prefix-icon\":\"\",\"suffix-icon\":\"\",\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field102\"},{\"__config__\":{\"label\":\"留言内容\",\"labelWidth\":null,\"showLabel\":true,\"tag\":\"el-input\",\"tagIcon\":\"el-icon-tickets\",\"required\":true,\"layout\":\"colFormItem\",\"span\":24,\"regList\":[],\"changeTag\":true,\"document\":\"https://element.eleme.cn/#/zh-CN/component/input\",\"formId\":103,\"renderKey\":\"1031672985791007\"},\"type\":\"textarea\",\"placeholder\":\"请输入留言内容\",\"autosize\":{\"minRows\":4,\"maxRows\":4},\"style\":{\"width\":\"100%\"},\"maxlength\":null,\"show-word-limit\":false,\"readonly\":false,\"disabled\":false,\"__vModel__\":\"field103\"}],\"formRef\":\"elForm\",\"formModel\":\"formData\",\"size\":\"medium\",\"labelPosition\":\"right\",\"labelWidth\":100,\"formRules\":\"rules\",\"gutter\":15,\"disabled\":false,\"span\":24,\"formBtns\":true}', NULL, 'formView?id=1611245185698656257', 'formManage/saveData', '*.*.*.*', '', 1, -1, NULL, NULL, '2023-01-06 14:16:02', -1, '2023-01-06 14:16:42', 2);

-- ----------------------------
-- Table structure for castle_help_article
-- ----------------------------
DROP TABLE IF EXISTS `castle_help_article`;
CREATE TABLE `castle_help_article`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文章内容',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `type_id` bigint NULL DEFAULT NULL COMMENT '类型id',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1491313274970341379 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '帮助中心文章' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_help_article
-- ----------------------------
INSERT INTO `castle_help_article` VALUES (1491261260857053186, '这是标题', '这是内容', '', 1491254791499640834, 1, -1, '2022-02-09 12:02:46', NULL, NULL, 2);
INSERT INTO `castle_help_article` VALUES (1491300380824276994, '如何退出登录', '系统设置-退出登录', '', 1491299650231046145, 1, -1, '2022-02-09 14:38:12', NULL, NULL, 2);
INSERT INTO `castle_help_article` VALUES (1491312931951771649, '常见问题1', '常见问题1内容阿巴阿巴', '', 1491254791499640834, 1, -1, '2022-02-09 15:28:05', NULL, NULL, 2);
INSERT INTO `castle_help_article` VALUES (1491313207760814081, '常见问题2', '常见问题2内容巴拉巴拉', '', 1491254791499640834, 1, -1, '2022-02-09 15:29:11', NULL, NULL, 2);
INSERT INTO `castle_help_article` VALUES (1491313274970341378, 'app使用问题1', '<p>这是<strong>内容</strong>123</p>\n', '', 1491299650231046145, 1, -1, '2022-02-09 15:29:27', -1, '2022-02-10 10:08:38', 2);

-- ----------------------------
-- Table structure for castle_help_article_type
-- ----------------------------
DROP TABLE IF EXISTS `castle_help_article_type`;
CREATE TABLE `castle_help_article_type`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型名称',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1491299650231046146 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '帮助中心文章类型' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_help_article_type
-- ----------------------------
INSERT INTO `castle_help_article_type` VALUES (1491254791499640834, '常见问题', '', 1, -1, '2022-02-09 11:37:03', -1, '2022-02-09 11:37:20', 2);
INSERT INTO `castle_help_article_type` VALUES (1491299650231046145, 'app使用', '', 1, -1, '2022-02-09 14:35:18', -1, '2022-02-09 14:35:39', 2);

-- ----------------------------
-- Table structure for castle_log_job
-- ----------------------------
DROP TABLE IF EXISTS `castle_log_job`;
CREATE TABLE `castle_log_job`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `task_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务名',
  `task_id` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '任务ID',
  `invoke_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '调用参数',
  `invoke_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用状态 00 成功 01 失败',
  `invoke_time` datetime NULL DEFAULT NULL COMMENT '调用时间',
  `elapsed_time` bigint NULL DEFAULT NULL COMMENT '耗时(毫秒)',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '定时任务调用日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_log_job
-- ----------------------------

-- ----------------------------
-- Table structure for castle_log_login
-- ----------------------------
DROP TABLE IF EXISTS `castle_log_login`;
CREATE TABLE `castle_log_login`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `invoke_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用路径',
  `remote_addr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用ip',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录地址',
  `cus_browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `cus_os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `invoke_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '调用参数',
  `invoke_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用状态 00 成功 01 失败',
  `invoke_time` datetime NULL DEFAULT NULL COMMENT '调用时间',
  `result_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '响应结果',
  `elapsed_time` bigint NULL DEFAULT NULL COMMENT '耗时(毫秒)',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `login_method` int NULL DEFAULT NULL COMMENT '登录方式(验证码/账号密码/微信/小程序等等)',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '登录操作日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_log_login
-- ----------------------------
INSERT INTO `castle_log_login` VALUES (1924644050420973569, '/login', '0:0:0:0:0:0:0:1', '内网地址', NULL, 'Unknown', 'admin', '00', '2025-05-20 09:51:14', '登录成功', NULL, '2025-05-20 09:51:14', 1, 1375360470249771009);
INSERT INTO `castle_log_login` VALUES (1924644134562906113, '/login', '0:0:0:0:0:0:0:1', '内网地址', NULL, 'Unknown', 'admin', '00', '2025-05-20 09:51:34', '登录成功', NULL, '2025-05-20 09:51:34', 1, 1375360470249771009);
INSERT INTO `castle_log_login` VALUES (1924644800672907266, '/login', '127.0.0.1', '内网地址', NULL, 'Windows 10', 'admin', '00', '2025-05-20 09:54:13', '登录成功', NULL, '2025-05-20 09:54:13', 1, 1375360470249771009);
INSERT INTO `castle_log_login` VALUES (1924644959305678849, '/login', '127.0.0.1', '内网地址', NULL, 'Windows 10', 'admin', '01', '2025-05-20 09:54:51', '用户名或密码错误', NULL, '2025-05-20 09:54:51', 1, 1375360470249771009);
INSERT INTO `castle_log_login` VALUES (1924644977756422146, '/login', '127.0.0.1', '内网地址', NULL, 'Windows 10', 'admin', '00', '2025-05-20 09:54:56', '登录成功', NULL, '2025-05-20 09:54:56', 1, 1375360470249771009);
INSERT INTO `castle_log_login` VALUES (1925015068008357889, '/login', '127.0.0.1', '内网地址', NULL, 'Windows 10', 'admin', '00', '2025-05-21 10:25:32', '登录成功', NULL, '2025-05-21 10:25:32', 1, 1375360470249771009);
INSERT INTO `castle_log_login` VALUES (1925015469805903873, '/login', '127.0.0.1', '内网地址', NULL, 'Windows 10', 'admin', '00', '2025-05-21 10:27:08', '登录成功', NULL, '2025-05-21 10:27:08', 1, 1375360470249771009);
INSERT INTO `castle_log_login` VALUES (1925077937647349761, '/login', '127.0.0.1', '内网地址', NULL, 'Windows 10', 'admin', '00', '2025-05-21 14:35:21', '登录成功', NULL, '2025-05-21 14:35:21', 1, 1375360470249771009);
INSERT INTO `castle_log_login` VALUES (1925078233526136833, '/login', '127.0.0.1', '内网地址', NULL, 'Windows 10', 'admin', '00', '2025-05-21 14:36:32', '登录成功', NULL, '2025-05-21 14:36:32', 1, 1375360470249771009);

-- ----------------------------
-- Table structure for castle_log_openapi
-- ----------------------------
DROP TABLE IF EXISTS `castle_log_openapi`;
CREATE TABLE `castle_log_openapi`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `remote_addr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'ip地址',
  `invoke_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用路径',
  `invoke_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '调用参数',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行类',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行方法',
  `invoke_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用状态 00 成功 01 失败',
  `secret_id` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'castle_api_secret表secret_id',
  `invoke_time` datetime NULL DEFAULT NULL COMMENT '调用时间',
  `result_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '响应结果',
  `elapsed_time` bigint NULL DEFAULT NULL COMMENT '耗时(毫秒)',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '对外开放api调用日志' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_log_openapi
-- ----------------------------

-- ----------------------------
-- Table structure for castle_log_operation
-- ----------------------------
DROP TABLE IF EXISTS `castle_log_operation`;
CREATE TABLE `castle_log_operation`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `invoke_url` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用路径',
  `invoke_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '调用参数',
  `class_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行类',
  `method_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '执行方法',
  `invoke_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用状态 00 成功 01 失败',
  `invoke_user_id` bigint NULL DEFAULT NULL COMMENT '调用人ID',
  `invoke_user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用人',
  `invoke_time` datetime NULL DEFAULT NULL COMMENT '调用时间',
  `result_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '响应结果',
  `elapsed_time` bigint NULL DEFAULT NULL COMMENT '耗时(毫秒)',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `oper_location` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作位置',
  `oper_type` int NULL DEFAULT NULL COMMENT '操作类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户操作记录日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_log_operation
-- ----------------------------

-- ----------------------------
-- Table structure for castle_log_sms
-- ----------------------------
DROP TABLE IF EXISTS `castle_log_sms`;
CREATE TABLE `castle_log_sms`  (
  `id` bigint NOT NULL COMMENT 'id',
  `sms_code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短信编码',
  `platform` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '平台类型',
  `mobile` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `params1` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数1',
  `params2` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数2',
  `params3` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数3',
  `params4` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '参数4',
  `status` tinyint UNSIGNED NULL DEFAULT NULL COMMENT '状态 0生效 1失效',
  `create_date` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `type` tinyint NULL DEFAULT NULL COMMENT '短信类型 0：注册验证码  1：登录验证码',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `idx_sms_code`(`sms_code` ASC) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '短信发送记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_log_sms
-- ----------------------------

-- ----------------------------
-- Table structure for castle_message_email_record
-- ----------------------------
DROP TABLE IF EXISTS `castle_message_email_record`;
CREATE TABLE `castle_message_email_record`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `sender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发送人',
  `send_time` datetime NULL DEFAULT NULL COMMENT '发送时间',
  `email_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件标题',
  `email_body` varchar(2000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件内容',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否已删除',
  `to_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '接收人',
  `to_cuser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '抄送人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '邮件发送记录表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_message_email_record
-- ----------------------------

-- ----------------------------
-- Table structure for castle_protocol
-- ----------------------------
DROP TABLE IF EXISTS `castle_protocol`;
CREATE TABLE `castle_protocol`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '协议编号',
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '协议标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '协议内容',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1703968313694285827 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '协议管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_protocol
-- ----------------------------
INSERT INTO `castle_protocol` VALUES (1486994536796225538, 'USER', '用户协议', '<p>我们重视用户的隐私。您在使用我们的服务时，我们可能会收集和使用您的相关信息。我们希望通过本《隐私政策》向您说明，在使用我们的服务时，我们如何收集、使用、储存和分享这些信息，以及我们为您提供的访问、更新、控制和保护这些信息的方式。本《隐私政策》与您所使用的服务息息相关，希望您仔细阅读，在需要时，按照本《隐私政策》的指引，作出您认为适当的选择。本《隐私政策》中涉及的相关技术词汇，我们尽量以简明扼要的表述，并提供进一步说明的链接，以便您的理解。您使用或继续使用我们的服务，即意味着同意我们按照本《隐私政策》收集、使用、储存和分享您的相关信息。如对本《隐私政策》或相关事宜有任何问题，请通过root@hcses.com与我们联系。</p>\n\n<p>我们提供服务时，可能会收集、储存和使用下列与您有关的信息。如果您不提供相关信息，可能无法享受我们提供的某些服务，或者无法达到相关服务拟达到的效果。设备或软件信息，例如您的移动设备、网页浏览器或用于接入我们服务的其他程序所提供的配置信息、您的IP地址和移动设备所用的版本和设备识别码； 您或其他用户提供的包含您所处地理位置的实时信息，例如您提供的账户信息中包含的您所在地区信息，您或其他人上传的显示您当前或曾经所处地理位置的共享信息；您可以通过关闭定位功能，停止对您的地理位置信息的收集。</p>\n', 1, -1, '2022-01-28 17:28:19', 1375360470249771009, '2023-09-25 14:52:48', 2);
INSERT INTO `castle_protocol` VALUES (1703942846509596673, 'USER', '测试', '<p>1. 我是测试</p>\n\n<p>. 我是二级测试</p>\n\n<ol>\n	<li style=\"text-align: right;\">232</li>\n	<li style=\"text-align: right;\">121</li>\n</ol>\n\n<p style=\"text-align: right;\">121</p>\n\n<p style=\"text-align: right;\">&nbsp;</p>\n\n<p style=\"text-align: right;\">&nbsp;</p>\n', 1, 1375360470249771009, '2023-09-19 09:23:27', 1375360470249771009, '2023-09-19 09:24:54', 1);
INSERT INTO `castle_protocol` VALUES (1703943331052371969, 'USER', 'A', '<p>A</p>\n', 1, 1375360470249771009, '2023-09-19 09:25:23', 1375360470249771009, '2023-09-19 09:40:19', 1);
INSERT INTO `castle_protocol` VALUES (1703947191074484226, 'USER', '', '', 1, 1375360470249771009, '2023-09-19 09:40:43', 1375360470249771009, '2023-09-19 09:41:09', 1);
INSERT INTO `castle_protocol` VALUES (1703968313694285826, 'USER', 'CES', '<p>更新日期：2021年09月28日</p>\n\n<p>生效日期：2021年09月28日</p>\n\n<p>山东省国际科技合作服务网严格遵守法律法规，遵循以下隐私保护原则，为您提供更加安全、可靠的服务：</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<ul>\n	<li><strong>1、安全可靠：</strong></li>\n	<li>我们竭尽全力通过合理有效的信息安全技术及管理流程，防止您的信息泄露、损毁、丢失。</li>\n	<li><strong>2、自主选择：</strong></li>\n	<li>我们为您提供便利的信息管理选项，以便您做出合适的选择，管理您的&nbsp;个人信息&nbsp;。</li>\n	<li><strong>3、最少够用：</strong></li>\n	<li>为了向您和其他用户提供更好的服务，我们仅收集必要的信息。</li>\n	<li><strong>4、清晰透明：</strong></li>\n	<li>我们努力使用简明易懂的表述，向您介绍隐私政策，以便您清晰地了解我们的信息处理方式。</li>\n</ul>\n\n<p>本《隐私政策》主要向您说明：</p>\n\n<p>&nbsp;</p>\n\n<p>一、<strong>我们收集哪些信息</strong></p>\n\n<p>&nbsp;</p>\n\n<p>二、<strong>我们收集信息的用途</strong></p>\n\n<p>&nbsp;</p>\n\n<p>三、<strong>您所享有的权利</strong></p>\n\n<p>&nbsp;</p>\n\n<p>希望您仔细阅读《隐私政策》（以下简称&ldquo;本政策&rdquo;），详细了解我们对信息的收集、使用方式，以便您更好地了解我们的服务并作出适当的选择。</p>\n\n<p>&nbsp;</p>\n\n<p>如您对本隐私政策条款有任何异议或疑问，您可通过本隐私政策第九条公布的联系方式与我公司联系。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h1>我们收集的内容</h1>\n\n<p>我们根据合法、正当、必要的原则，仅收集实现产品功能所必要的&nbsp;信息&nbsp;。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h3>您在使用我们服务时主动提供的信息</h3>\n\n<p><strong>您在注册帐户时填写的信息</strong></p>\n\n<p>&nbsp;</p>\n\n<p>例如，您在注册用户时所填写的昵称、手机号码。</p>\n\n<p>&nbsp;</p>\n\n<p><strong>您在使用服务时上传的信息</strong></p>\n\n<p>&nbsp;</p>\n\n<p>例如，您在使用APP时，上传的头像和照片。</p>\n\n<p>&nbsp;</p>\n\n<p><strong>您通过我们的客服或参加我们举办的活动时所提交的信息</strong></p>\n\n<p>&nbsp;</p>\n\n<p>例如，您参与我们线上活动时填写的调查问卷中可能包含您的姓名、电话、家庭地址等信息。</p>\n\n<p>&nbsp;</p>\n\n<p><strong>我们的部分服务可能需要您提供特定的个人敏感信息来实现特定功能。若您选择不提供该类信息，则可能无法正常使用服务中的特定功能，但不影响您使用服务中的其他功能。若您主动提供您的&nbsp;个人敏感信息&nbsp;即表示您同意我们按本政策所述目的和方式来处理您的个人敏感信息。</strong></p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h3>我们在您使用服务时获取的信息</h3>\n\n<p>**日志信息。**当您使用我们的服务时，我们可能会自动收集相关信息并存储为服务日志信息。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<ul>\n	<li><strong>1)设备信息</strong></li>\n	<li>例如，&nbsp;设备&nbsp;型号、操作系统版本、&nbsp;唯一设备标识符&nbsp;、电池、信号强度等信息。</li>\n	<li><strong>2)软件信息</strong></li>\n	<li>例如，软件的版本号、浏览器类型。为确保操作环境的安全或提供服务所需，我们会收集有关您使用的移动应用和其他软件的信息。</li>\n	<li><strong>3)&nbsp;IP地址</strong></li>\n	<li><strong>4)服务日志信息</strong></li>\n	<li>例如，您在使用我们服务时搜索、查看的信息、服务故障信息、引荐网址等信息。</li>\n	<li><strong>5)通讯日志信息</strong></li>\n	<li>例如，您在使用我们服务时曾经通讯的账户、通讯时间和时长。</li>\n</ul>\n\n<p><strong>位置信息</strong></p>\n\n<p>&nbsp;</p>\n\n<p>当您使用与位置有关的服务时，我们可能会记录您设备所在的位置信息，以便为您提供相关服务。</p>\n\n<p>&nbsp;</p>\n\n<p>在您使用服务时，我们可能会通过&nbsp;IP地址&nbsp;、&nbsp;GPS&nbsp;、WLAN（如&nbsp;WiFi&nbsp;)或&nbsp;基站&nbsp;等途径获取您的地理位置信息;</p>\n\n<p>&nbsp;</p>\n\n<p>您或其他用户在使用服务时提供的信息中可能包含您所在地理位置信息，例如您提供的帐号信息中可能包含的您所在地区信息，您或其他人共享的照片包含的地理标记信息。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h1><strong>我们如何使用收集的信息</strong></h1>\n\n<p>我们严格遵守法律法规的规定及与用户的约定，将收集的信息用于以下用途。若我们超出以下用途使用您的信息，我们将再次向您进行说明，并征得您的同意。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h3>向您提供服务</h3>\n\n<p>满足您的个性化需求</p>\n\n<p>例如，语言设定、位置设定、个性化的帮助服务。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h3>安全保障</h3>\n\n<p>例如，我们会将您的信息用于身份验证、安全防范、存档备份、客户的安全服务等用途。例如，您下载或安装的安全软件会对恶意程序或病毒进行检测，或为您识别诈骗信息。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h3>向您推荐您可能感兴趣的广告、资讯等</h3>\n\n<h3>评估、改善我们的广告投放和其他促销及推广活动的效果</h3>\n\n<h3>管理软件</h3>\n\n<p>例如，进行软件认证、软件升级等。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h3>邀请您参与有关我们服务的调查</h3>\n\n<p><strong>为了让您有更好的体验、改善我们的服务或经您同意的其他用途，在符合相关法律法规的前提下，我们可能将通过某些服务所收集的信息用于我们的其他服务。例如，将您在使用我们某项服务时的信息，用于另一项服务中向您展示个性化的内容或广告、用于用户研究分析与统计等服务。</strong></p>\n\n<p>&nbsp;</p>\n\n<p>为了确保服务的安全，帮助我们更好地了解我们应用程序的运行情况，我们可能记录相关信息，例如，您使用应用程序的频率、故障信息、总体使用情况、性能数据以及应用程序的来源。我们不会将我们存储在分析软件中的信息与您在应用程序中提供的个人身份信息相结合。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h1><strong>我们如何使用Cookie、标识符及相关技术</strong></h1>\n\n<p>我们或我们的第三方合作伙伴，可能通过放置安全的Cookie、标识符及相关技术收集您的信息，以便为您提供更个性化的用户体验和服务。我们会严格要求第三方合作伙伴遵守本政策的相关规定。</p>\n\n<p>&nbsp;</p>\n\n<p>您也可以通过浏览器设置管理Cookie。但请注意，如果停用Cookie，您可能无法享受最佳的服务体验，某些服务也可能无法正常使用。若您希望了解更多Cookie的安全性等信息，可参见《Cookie政策说明》。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h1><strong>您如何管理自己的信息</strong></h1>\n\n<p>您可以在使用我们服务的过程中，访问、修改和删除您提供的注册信息和其他个人信息，也可按照通知指引与我们联系。您访问、修改和删除个人信息的范围和方式将取决于您使用的具体服务。</p>\n\n<p>&nbsp;</p>\n\n<p>例如，若您在使用地理位置相关服务时，希望停止分享您的地理位置信息，您可通过手机定位关闭功能、软硬件服务商及通讯服务提供商的关闭方式停止分享，建议您仔细阅读相关指引。</p>\n\n<p>&nbsp;</p>\n\n<p>我们将按照本政策所述，仅为实现我们产品或服务的功能，收集、使用您的信息。</p>\n\n<p>&nbsp;</p>\n\n<p>如您发现我们违反法律、行政法规的规定或者双方的约定收集、使用您的个人信息，您可以要求我们删除。</p>\n\n<p>&nbsp;</p>\n\n<p>如您发现我们收集、存储的您的个人信息有错误的，您也可以要求我们更正。</p>\n\n<p>&nbsp;</p>\n\n<p>请通过本政策列明的联系方式与我们联系。</p>\n\n<p>&nbsp;</p>\n\n<p>在您访问、修改和删除相关信息时，我们可能会要求您进行身份验证，以保障帐号的安全。</p>\n\n<p>&nbsp;</p>\n\n<p>请您理解，由于技术所限、法律或监管要求，我们可能无法满足您的所有要求，我们会在合理的期限内答复您的请求。</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<h1><strong>我们分享的信息</strong></h1>\n\n<p>我们遵照法律法规的规定，对信息的分享进行严格的限制，例如：</p>\n\n<p>&nbsp;</p>\n\n<p>经您事先同意，我们可能与第三方分享您的个人信息</p>\n\n<p>&nbsp;</p>\n\n<p>我们需要向第三方合作伙伴等，分享已经匿名化或去标识化处理后的信息，要求其严格遵守我们关于数据隐私保护的措施与要求，包括但不限于根据数据保护协议、承诺书及相关数据处理政策进行处理，避免识别出个人身份，保障隐私安全。该等分享将主要用于以下用途：</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<ul>\n	<li>向您提供我们的服务；</li>\n	<li>实现&ldquo;我们如何使用收集的信息&rdquo;部分所述目的；</li>\n	<li>履行我们在《服务协议&nbsp;》或本政策中的义务和行使我们的权利；</li>\n	<li>理解、维护和改善我们的服务。</li>\n</ul>\n\n<p>我们不会向合作伙伴分享可用于识别您个人身份的信息（例如您的姓名或电子邮件地址），除非您明确授权。</p>\n\n<p>&nbsp;</p>\n\n<p>随着我们业务的持续发展，当发生合并、收购、资产转让等交易导致向第三方分享您的个人信息时，我们将通过推送通知、公告等形式告知您相关情形，按照法律法规及不低于本政策所要求的标准继续保护或要求新的管理者继续保护您的个人信息。</p>\n\n<p>&nbsp;</p>\n\n<p>我们会将所收集到的信息用于大数据分析。</p>\n\n<p>&nbsp;</p>\n\n<p>例如，我们将收集到的信息用于分析形成不包含任何个人信息的城市热力图或行业洞察报告。</p>\n\n<p>我们可能对外公开并与我们的合作伙伴分享经统计加工后不含身份识别内容的信息，用于了解用户如何使用我们服务或让公众了解我们服务的总体使用趋势。</p>\n\n<p>我们可能基于以下目的披露您的个人信息</p>\n\n<p>&nbsp;</p>\n\n<p>&nbsp;</p>\n\n<ul>\n	<li>遵守适用的法律法规等有关规定；</li>\n	<li>遵守法院判决、裁定或其他法律程序的规定；</li>\n	<li>遵守相关政府机关或其他法定授权组织的要求；</li>\n	<li>我们有理由确信需要遵守法律法规等有关规定；</li>\n	<li>为执行相关服务协议或本政策、维护社会公共利益，为保护我们的客户、我们或我们的关联公司、其他用户或雇员的人身财产安全或其他合法权益合理且必要的用途。</li>\n</ul>\n', 2, 1375360470249771009, '2023-09-19 11:04:39', 1375360470249771009, '2023-09-22 10:27:32', 2);

-- ----------------------------
-- Table structure for castle_sys_captcha
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_captcha`;
CREATE TABLE `castle_sys_captcha`  (
  `id` bigint NOT NULL COMMENT '主键',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `captcha` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '验证码',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '手机验证码' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_captcha
-- ----------------------------
INSERT INTO `castle_sys_captcha` VALUES (1415202859353014274, '13200000000', '701767', '2021-07-14 15:03:45');
INSERT INTO `castle_sys_captcha` VALUES (1415202925778206721, '13200000001', '258655', '2021-07-15 14:54:05');
INSERT INTO `castle_sys_captcha` VALUES (1415204964218339330, '17863626302', '508280', '2021-07-21 16:34:17');
INSERT INTO `castle_sys_captcha` VALUES (1415205635256647681, '13222222222', '541287', '2021-07-14 15:16:14');
INSERT INTO `castle_sys_captcha` VALUES (1415976090133590017, '14512854452', '903741', '2021-07-16 18:06:22');
INSERT INTO `castle_sys_captcha` VALUES (1415976746319867905, '17863645263', '055542', '2021-07-16 18:08:59');
INSERT INTO `castle_sys_captcha` VALUES (1420233337787756546, '18663615557', '451217', '2021-07-28 12:03:09');
INSERT INTO `castle_sys_captcha` VALUES (1420233876319612929, '15653459345', '680879', '2021-07-28 12:05:17');
INSERT INTO `castle_sys_captcha` VALUES (1420589796888842242, '18811494406', '918058', '2022-06-15 15:53:49');
INSERT INTO `castle_sys_captcha` VALUES (1536960630972268545, '15553663472', '289741', '2022-06-15 14:35:44');
INSERT INTO `castle_sys_captcha` VALUES (1536977670504185858, '13280159716', '697130', '2022-06-15 15:54:13');

-- ----------------------------
-- Table structure for castle_sys_dept
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_dept`;
CREATE TABLE `castle_sys_dept`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '部门名称',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '部门描述',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '上级部门',
  `parents` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '所有上级',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes生效；no失效',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1707241015288803331 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_dept
-- ----------------------------
INSERT INTO `castle_sys_dept` VALUES (1587349075955404802, '技术一部', '', 1418453438076858370, '1418453438076858370', 1, -1, '2022-11-01 15:41:26', -1, '2022-11-01 15:49:02', 1);
INSERT INTO `castle_sys_dept` VALUES (1587349238514044929, '华创数字云', '', NULL, NULL, 1, -1, '2022-11-01 15:42:05', 1375360470249771009, '2023-09-26 11:05:15', 2);
INSERT INTO `castle_sys_dept` VALUES (1587349375047028738, '技术一部', '', 1587349238514044929, '1587349238514044929', 1, -1, '2022-11-01 15:42:37', 1375360470249771009, '2023-09-15 14:38:33', 1);
INSERT INTO `castle_sys_dept` VALUES (1587349475806793730, '技术二部', '', 1587349238514044929, '1587349238514044929', 1, -1, '2022-11-01 15:43:01', 1375360470249771009, '2023-09-15 14:38:19', 1);
INSERT INTO `castle_sys_dept` VALUES (1587349550989692930, '技术三部', '', 1587349238514044929, '1587349238514044929', 1, -1, '2022-11-01 15:43:19', 1375360470249771009, '2023-09-15 14:38:27', 1);
INSERT INTO `castle_sys_dept` VALUES (1587349605343678465, '技术一组', '', 1587349375047028738, '1587349238514044929,1587349375047028738', 1, -1, '2022-11-01 15:43:32', 1375360470249771009, '2023-09-15 14:38:30', 1);
INSERT INTO `castle_sys_dept` VALUES (1648896088104521729, 'W机构', '', NULL, NULL, 1, -1, '2023-04-20 11:47:17', -1, '2023-09-15 14:56:19', 1);
INSERT INTO `castle_sys_dept` VALUES (1648896133931487234, 'C部门', '', 1648896088104521729, '1648896088104521729', 1, -1, '2023-04-20 11:47:28', -1, '2023-09-15 14:56:16', 1);
INSERT INTO `castle_sys_dept` VALUES (1648896202600632322, 'A部门', '', 1648896088104521729, '1648896088104521729', 1, -1, '2023-04-20 11:47:44', -1, '2023-05-20 11:14:03', 1);
INSERT INTO `castle_sys_dept` VALUES (1648896385325486081, 'A1部门', '', 1648896202600632322, '1648896088104521729,1648896202600632322', 1, -1, '2023-04-20 11:48:28', -1, '2023-05-20 11:14:01', 1);
INSERT INTO `castle_sys_dept` VALUES (1659800293019295745, '测试1', '测试', 1587349475806793730, '1587349238514044929,1587349475806793730', NULL, 1375360470249771009, '2023-05-20 13:56:42', 1375360470249771009, '2023-09-15 14:38:17', 1);
INSERT INTO `castle_sys_dept` VALUES (1659800432165330945, '测试wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww', '测试', 1587349605343678465, '1587349238514044929,1587349375047028738,1587349605343678465', NULL, 1375360470249771009, '2023-05-20 13:57:15', 1375360470249771009, '2023-09-15 14:38:24', 1);
INSERT INTO `castle_sys_dept` VALUES (1663002325972680706, '测试', '', 1659800293019295745, '1587349238514044929,1587349475806793730,1659800293019295745', NULL, 1375360470249771009, '2023-05-29 10:00:26', 1375360470249771009, '2023-09-15 14:34:44', 1);
INSERT INTO `castle_sys_dept` VALUES (1663017720662196225, '厕所111', '2121', 1659800293019295745, '1587349238514044929,1587349475806793730,1659800293019295745', NULL, 1660534060583600130, '2023-05-29 11:01:37', 1375360470249771009, '2023-09-15 14:34:40', 1);
INSERT INTO `castle_sys_dept` VALUES (1663017981870866433, '测试', '', 1648896133931487234, '1648896088104521729,1648896133931487234', NULL, 1375360470249771009, '2023-05-29 11:02:39', -1, '2023-09-15 14:56:14', 1);
INSERT INTO `castle_sys_dept` VALUES (1663023730856558594, '222', '222', 1587349475806793730, '1587349238514044929,1587349475806793730', NULL, 1660534060583600130, '2023-05-29 11:25:30', 1375360470249771009, '2023-09-15 14:34:47', 1);
INSERT INTO `castle_sys_dept` VALUES (1663076581607862274, '主管部门', '', NULL, NULL, NULL, 1375360470249771009, '2023-05-29 14:55:30', -1, '2023-09-15 14:56:06', 1);
INSERT INTO `castle_sys_dept` VALUES (1663076674952097794, '你好', '', 1663076581607862274, '1663076581607862274', NULL, 1375360470249771009, '2023-05-29 14:55:52', -1, '2023-09-15 14:56:03', 1);
INSERT INTO `castle_sys_dept` VALUES (1663729838902833154, 'xsss', '', 1587349238514044929, '1587349238514044929', NULL, -1, '2023-05-31 10:11:19', 1375360470249771009, '2023-09-15 14:39:50', 1);
INSERT INTO `castle_sys_dept` VALUES (1663729939838758913, 'ces', '', 1663729838902833154, '1587349238514044929,1663729838902833154', NULL, 1375360470249771009, '2023-05-31 10:11:43', 1375360470249771009, '2023-09-15 14:38:35', 1);
INSERT INTO `castle_sys_dept` VALUES (1702580856163680258, '技术部门', '', 1587349238514044929, '1587349238514044929', NULL, 1375360470249771009, '2023-09-15 15:11:23', NULL, NULL, 2);
INSERT INTO `castle_sys_dept` VALUES (1702580953790300161, '商务部门', '', 1587349238514044929, '1587349238514044929', NULL, 1375360470249771009, '2023-09-15 15:11:46', NULL, NULL, 2);
INSERT INTO `castle_sys_dept` VALUES (1702581010535038977, '售前部门', '', 1587349238514044929, '1587349238514044929', NULL, 1375360470249771009, '2023-09-15 15:12:00', NULL, NULL, 2);
INSERT INTO `castle_sys_dept` VALUES (1704429780004610049, '售后部门', '企业为负责维护企业产品购买者与企业之间的利益关系，树立企业良好形象所设立的服务部门。', 1587349238514044929, '1587349238514044929', NULL, 1375360470249771009, '2023-09-20 17:38:21', 1375360470249771009, '2023-09-20 17:38:39', 2);
INSERT INTO `castle_sys_dept` VALUES (1704432121546121217, '前端', '', 1587349238514044929, '1587349238514044929', NULL, 1375360470249771009, '2023-09-20 17:47:39', 1375360470249771009, '2023-09-20 17:47:59', 1);
INSERT INTO `castle_sys_dept` VALUES (1704432192840900609, '前端', '', 1702580856163680258, '1587349238514044929,1702580856163680258', NULL, 1375360470249771009, '2023-09-20 17:47:56', NULL, NULL, 2);
INSERT INTO `castle_sys_dept` VALUES (1704432588149858305, '后端', '', 1702580856163680258, '1587349238514044929,1702580856163680258', NULL, 1375360470249771009, '2023-09-20 17:49:31', NULL, NULL, 2);
INSERT INTO `castle_sys_dept` VALUES (1704432959899410434, '设计部门', '', 1587349238514044929, '1587349238514044929', NULL, 1375360470249771009, '2023-09-20 17:50:59', NULL, NULL, 2);
INSERT INTO `castle_sys_dept` VALUES (1704433059363135490, '会计部门', '', 1587349238514044929, '1587349238514044929', NULL, 1375360470249771009, '2023-09-20 17:51:23', 1375360470249771009, '2023-09-28 11:48:50', 1);
INSERT INTO `castle_sys_dept` VALUES (1704433229630906370, '测试', '', 1702580856163680258, '1587349238514044929,1702580856163680258', NULL, 1375360470249771009, '2023-09-20 17:52:03', NULL, NULL, 2);
INSERT INTO `castle_sys_dept` VALUES (1705040017703059458, '前端001组', '', 1704432192840900609, '1587349238514044929,1702580856163680258,1704432192840900609', NULL, 1375360470249771009, '2023-09-22 10:03:13', 1375360470249771009, '2023-09-28 15:34:11', 1);
INSERT INTO `castle_sys_dept` VALUES (1705040145591582721, '四代', '', 1705040017703059458, '1587349238514044929,1702580856163680258,1704432192840900609,1705040017703059458', NULL, 1375360470249771009, '2023-09-22 10:03:43', 1375360470249771009, '2023-09-26 16:25:04', 1);
INSERT INTO `castle_sys_dept` VALUES (1705040258338668545, 'PS', '', 1704432959899410434, '1587349238514044929,1704432959899410434', NULL, 1375360470249771009, '2023-09-22 10:04:10', NULL, NULL, 2);
INSERT INTO `castle_sys_dept` VALUES (1705041446383665153, '测试删除', '', 1705040145591582721, '1587349238514044929,1702580856163680258,1704432192840900609,1705040017703059458,1705040145591582721', NULL, 1375360470249771009, '2023-09-22 10:08:54', 1375360470249771009, '2023-09-26 16:25:00', 1);
INSERT INTO `castle_sys_dept` VALUES (1705045089220575234, '1111', '', 1705040145591582721, '1587349238514044929,1702580856163680258,1704432192840900609,1705040017703059458,1705040145591582721', NULL, 1375360470249771009, '2023-09-22 10:23:22', 1375360470249771009, '2023-09-22 11:16:43', 1);
INSERT INTO `castle_sys_dept` VALUES (1707241015288803330, '总办部', '', 1587349238514044929, '1587349238514044929', NULL, 1375360470249771009, '2023-09-28 11:49:12', NULL, NULL, 2);

-- ----------------------------
-- Table structure for castle_sys_dict
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_dict`;
CREATE TABLE `castle_sys_dict`  (
  `id` bigint NOT NULL COMMENT '主键',
  `parent_id` bigint NULL DEFAULT 0 COMMENT '父主键',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典码',
  `dict_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典值',
  `dict_value` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名称',
  `sort` int NULL DEFAULT NULL COMMENT '排序',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典备注',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes生效；no失效',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT 0 COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_dict
-- ----------------------------
INSERT INTO `castle_sys_dict` VALUES (1399982100989038594, 0, 'idcardType', '-1', '证件类型', NULL, '勿删', 1, -1, '2021-06-02 14:51:58', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1399982216894435330, 1399982100989038594, 'idcardType', '1', '身份证', 1, '', 1, -1, '2021-06-02 14:52:26', -1, '2021-06-09 14:29:35', 1);
INSERT INTO `castle_sys_dict` VALUES (1399982281910341633, 1399982100989038594, 'idcardType', '2', '驾驶证', 2, '', 1, -1, '2021-06-02 14:52:41', -1, '2021-06-09 14:29:42', 1);
INSERT INTO `castle_sys_dict` VALUES (1415611997182750721, 1399982100989038594, 'idcardType', '234', '123', 1, '&lt;&gt;123&lt;/&gt;', 1, -1, '2021-07-15 17:59:36', -1, '2021-07-16 08:51:05', 1);
INSERT INTO `castle_sys_dict` VALUES (1418463740902289409, 0, 'sex', '-1', '性别', NULL, '性别', 1, -1, '2021-07-23 14:51:24', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1418463784539828225, 1418463740902289409, 'sex', '1', '男', 1, '男性', 1, -1, '2021-07-23 14:51:35', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1418463806006276098, 1418463740902289409, 'sex', '2', '女', 2, '女性', 1, -1, '2021-07-23 14:51:40', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1418463839078363137, 1418463740902289409, 'sex', '3', '中性', 3, '', 1, -1, '2021-07-23 14:51:48', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1418463862759403521, 1418463740902289409, 'sex', '4', '变性人', 4, '', 1, -1, '2021-07-23 14:51:53', -1, '2021-07-23 14:52:07', 2);
INSERT INTO `castle_sys_dict` VALUES (1418463892358606850, 1418463740902289409, 'sex', '5', '保密', 5, '', 1, -1, '2021-07-23 14:52:00', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1433003223808671745, 0, '2', '-1', '2', NULL, '2', 1, -1, '2021-09-01 17:46:07', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1451077701448982530, 0, 'dictDemo', '-1', '字典示例', NULL, '用于框架组件演示页面，勿删！！！', 1, -1, '2021-10-21 14:47:38', -1, '2022-01-13 10:24:21', 2);
INSERT INTO `castle_sys_dict` VALUES (1451077801999032321, 1451077701448982530, 'dictDemo', 'dictDemo001', '示例11', 1, '', 1, -1, '2021-10-21 14:48:02', -1, '2022-01-13 10:22:40', 2);
INSERT INTO `castle_sys_dict` VALUES (1451077858492112898, 1451077701448982530, 'dictDemo', 'dictDemo002', '示例2', 2, '', 1, -1, '2021-10-21 14:48:16', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1462963000022437890, 0, 'source', '-1', '会员来源', NULL, '', 1, -1, '2021-11-23 09:55:34', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1464122691335266306, 0, 'withdraw', '-1', '余额提现方式', NULL, '', 1, -1, '2021-11-26 14:43:46', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1471685912124162049, 0, 'idcardType', '-1', '证件类型', NULL, '勿删！！！勿删！！！勿删！！！', 1, -1, '2021-12-17 11:37:19', -1, '2021-12-17 11:37:48', 2);
INSERT INTO `castle_sys_dict` VALUES (1471686244933795842, 1471685912124162049, 'idcardType', '1', '身份证', 1, '', 1, -1, '2021-12-17 11:38:38', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1481459428293824513, 0, '123', '-1', '123', NULL, '', 1, -1, '2022-01-13 10:53:47', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1481460540057014274, 0, '123', '-1', '123', NULL, '', 1, -1, '2022-01-13 10:58:12', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1481460951702786049, 0, '123', '-1', '123', NULL, '', 1, -1, '2022-01-13 10:59:50', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1481461234071719937, 0, '123', '-1', '123', NULL, '', 1, -1, '2022-01-13 11:00:57', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1481461824134795265, 0, '123', '-1', '123', NULL, '', 1, -1, '2022-01-13 11:03:18', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1481461868061741057, 0, '3', '-1', '3', NULL, '', 1, -1, '2022-01-13 11:03:28', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1481462031962558466, 1481461868061741057, '3', '1', '1', 1, '', 1, -1, '2022-01-13 11:04:07', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1481462687406444545, 0, '1', '-1', '1', NULL, '', 1, -1, '2022-01-13 11:06:44', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1481463283035361282, 1451077701448982530, 'dictDemo', '3', '3', 1, '', 1, -1, '2022-01-13 11:09:06', NULL, NULL, 1);
INSERT INTO `castle_sys_dict` VALUES (1554351964527722497, 1471685912124162049, 'idcardType', '2', '护照', 2, '', 1, -1, '2022-08-02 14:22:41', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1554352072161951745, 1471685912124162049, 'idcardType', '3', '驾驶证', 3, '', 1, -1, '2022-08-02 14:23:07', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1587335630248030210, 0, 'feedback', '-1', '意见反馈类型', NULL, '', 1, -1, '2022-11-01 14:48:00', -1, '2022-11-24 14:18:33', 2);
INSERT INTO `castle_sys_dict` VALUES (1587335734103191553, 1587335630248030210, 'feedback', '1', '功能异常', 1, '', 1, -1, '2022-11-01 14:48:25', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1587335774813106177, 1587335630248030210, 'feedback', '2', '体验问题', 2, '', 1, -1, '2022-11-01 14:48:35', -1, '2022-11-01 14:49:03', 2);
INSERT INTO `castle_sys_dict` VALUES (1587335816575791106, 1587335630248030210, 'feedback', '3', '功能建议', 3, '', 1, -1, '2022-11-01 14:48:45', -1, '2022-11-01 14:49:11', 2);
INSERT INTO `castle_sys_dict` VALUES (1587335846787362818, 1587335630248030210, 'feedback', '4', '其他问题', 4, '', 1, -1, '2022-11-01 14:48:52', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1595714959375892481, 0, '123111', '-1', '123', NULL, '', 1, -1, '2022-11-24 17:44:28', -1, '2022-11-24 17:44:44', 1);
INSERT INTO `castle_sys_dict` VALUES (1601462208294215682, 1462963000022437890, 'source', '1', '微信公众号', 1, '', 1, -1, '2022-12-10 14:21:59', -1, '2022-12-10 14:22:26', 2);
INSERT INTO `castle_sys_dict` VALUES (1601462264678244354, 1462963000022437890, 'source', '2', 'APP端', 1, '', 1, -1, '2022-12-10 14:22:12', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1601462390087933953, 1462963000022437890, 'source', '3', 'H5端', 1, '', 1, -1, '2022-12-10 14:22:42', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1601462445356277762, 1462963000022437890, 'source', '4', '微信小程序', 1, '', 1, -1, '2022-12-10 14:22:55', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1640940870695727106, 0, 'hots', '-1', '热点标识', NULL, '', 1, -1, '2023-03-29 12:56:05', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1640940922013036546, 1640940870695727106, 'hots', '1', '是', 1, '', 1, -1, '2023-03-29 12:56:18', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1640940945085902850, 1640940870695727106, 'hots', '2', '否', 1, '', 1, -1, '2023-03-29 12:56:23', NULL, NULL, 2);
INSERT INTO `castle_sys_dict` VALUES (1640981308647411713, 0, 'status', '-1', '状态', NULL, '启用状态', 1, -1, '2023-03-29 15:36:47', NULL, NULL, 2);

-- ----------------------------
-- Table structure for castle_sys_industry
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_industry`;
CREATE TABLE `castle_sys_industry`  (
  `id` bigint NOT NULL COMMENT 'id',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '上级ID，一级为0',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `tree_level` tinyint NULL DEFAULT NULL COMMENT '层级',
  `leaf` tinyint NULL DEFAULT NULL COMMENT '是否叶子节点  0：否   1：是',
  `shortpinyin` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '短拼音',
  `sort` bigint NULL DEFAULT NULL COMMENT '排序',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '行业职位' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_industry
-- ----------------------------

-- ----------------------------
-- Table structure for castle_sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_menu`;
CREATE TABLE `castle_sys_menu`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `parent_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '父级ID',
  `permissions` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '授权编码,多个用分号隔开',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `url` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '链接的url',
  `icon` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '菜单的icon',
  `type` int NULL DEFAULT NULL COMMENT '菜单类型 MenuTypeEnum',
  `view_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '页面路径(vue前端必填）',
  `sort` int NULL DEFAULT 0 COMMENT '排序字段',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes生效；no失效',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1672885152270192643 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_menu
-- ----------------------------
INSERT INTO `castle_sys_menu` VALUES (1356116542443208706, 1344564277731368962, '', '测试按钮', NULL, '', 3, '', 1, '', NULL, -1, '2021-02-01 13:45:54', NULL, NULL, 2);
INSERT INTO `castle_sys_menu` VALUES (1656842546896564225, NULL, '', '应用中心', NULL, 'el-icon-platform-eleme', 1, '/knowledgeClassification', 2, '', 1, -1, '2023-05-12 10:03:40', -1, '2023-05-12 10:04:13', 2);
INSERT INTO `castle_sys_menu` VALUES (1656842923851247617, 1656842546896564225, 'knowledge:kbSubjectWarehouse:pageList', '主题知识库', NULL, 'el-icon-check', 1, '/themeKnowledgeBase', 1, '', 1, -1, '2023-05-12 10:05:10', -1, '2023-05-12 14:06:51', 2);
INSERT INTO `castle_sys_menu` VALUES (1656867256074047489, NULL, '', '组织权限', NULL, 'el-icon-platform-eleme', 1, '/organizational', 1, '', 1, -1, '2023-05-12 11:41:52', NULL, NULL, 2);
INSERT INTO `castle_sys_menu` VALUES (1656867382108688385, 1656867256074047489, 'system:sysDept:list;system:sysUser:pageList', '组织架构', NULL, 'el-icon-platform-eleme', 1, '/organizationalPermissions', 1, '', 1, -1, '2023-05-12 11:42:22', -1, '2023-05-12 16:38:17', 2);
INSERT INTO `castle_sys_menu` VALUES (1656867473682927617, 1656867256074047489, 'system:sysUser:pageList;knowledge:kbGroups:list;knowledge:kbGroups:info;system:sysDept:list;knowledge:kbGroups:listFind', '群组', NULL, 'el-icon-platform-eleme', 1, '/group', 2, '', 1, -1, '2023-05-12 11:42:43', -1, '2023-05-12 14:00:57', 2);
INSERT INTO `castle_sys_menu` VALUES (1656868013343051778, 1656867256074047489, 'system:sysRole:pageList', '权限管理', NULL, 'el-icon-platform-eleme', 1, '/assignment', 3, '', 1, -1, '2023-05-12 11:44:52', -1, '2023-05-12 11:45:02', 2);
INSERT INTO `castle_sys_menu` VALUES (1656868420572221442, 1656867382108688385, 'system:sysUser:pageList', '查询', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 11:46:29', -1, '2023-06-16 16:11:29', 2);
INSERT INTO `castle_sys_menu` VALUES (1656868500343689217, 1656867382108688385, 'system:sysUser:save', '新增', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 11:46:48', -1, '2023-05-12 11:47:40', 2);
INSERT INTO `castle_sys_menu` VALUES (1656868611425636354, 1656867382108688385, 'system:sysUser:edit;system:sysUser:info;system:sysPost:list', '编辑', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 11:47:15', -1, '2023-05-12 16:56:20', 2);
INSERT INTO `castle_sys_menu` VALUES (1656868672599560194, 1656867382108688385, 'system:sysUser:delete;system:sysDept:delete', '删除', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 11:47:29', -1, '2023-05-12 16:51:27', 2);
INSERT INTO `castle_sys_menu` VALUES (1656868824257204225, 1656867382108688385, 'system:sysUser:setStatus', '置为无效', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 11:48:05', -1, '2023-05-12 17:05:09', 2);
INSERT INTO `castle_sys_menu` VALUES (1656904019786182658, NULL, '', '模板引擎', NULL, 'el-icon-picture', 1, '/attribute', 3, '', 1, -1, '2023-05-12 14:07:57', -1, '2023-05-12 14:22:15', 2);
INSERT INTO `castle_sys_menu` VALUES (1656904117437968386, 1656904019786182658, '/kb/kbModel/page;kb:kbModel:info;knowledge:kbCategory:pageList', '属性模板', NULL, 'el-icon-delete', 1, '/attributeTemplate', 1, '', 1, -1, '2023-05-12 14:08:20', -1, '2023-05-13 14:13:26', 2);
INSERT INTO `castle_sys_menu` VALUES (1656947862212444162, 1656904117437968386, 'kb:kbModel:save', '新增', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 17:02:10', -1, '2023-05-13 14:11:15', 2);
INSERT INTO `castle_sys_menu` VALUES (1656949023917211650, 1656867382108688385, 'system:sysDept:save', '新增部门', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 17:06:47', NULL, NULL, 2);
INSERT INTO `castle_sys_menu` VALUES (1656954394169835521, 1656868013343051778, 'system:sysRole:save;system:sysRole:menuAuthList;system:sysRole:saveToAuth', '新增', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 17:28:07', -1, '2023-05-12 17:30:09', 2);
INSERT INTO `castle_sys_menu` VALUES (1656954456983732225, 1656868013343051778, 'system:sysRole:delete', '删除', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 17:28:22', -1, '2023-05-12 17:29:49', 2);
INSERT INTO `castle_sys_menu` VALUES (1656954571421122562, 1656868013343051778, 'system:sysRole:deleteBatch', '批量删除', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 17:28:49', -1, '2023-06-03 11:24:12', 2);
INSERT INTO `castle_sys_menu` VALUES (1656954609102749698, 1656868013343051778, 'system:sysRole:edit;system:sysRole:menuAuthList;system:sysRole:menuAuthInfoToRole;system:sysRole:editToAuth', '编辑', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 17:28:58', -1, '2023-05-12 17:30:00', 2);
INSERT INTO `castle_sys_menu` VALUES (1656956377199017986, 1656842546896564225, 'knowledge:kbSubjectWarehouse:pageList;knowledge:kbCategory:findBySwWidToBackstage;knowledge:kbCategory:findById', '知识分类', NULL, 'el-icon-s-custom', 1, '/knowledgeClassification', 2, '', 1, -1, '2023-05-12 17:36:00', -1, '2023-05-13 09:50:00', 2);
INSERT INTO `castle_sys_menu` VALUES (1656958667213504513, 1656956377199017986, 'knowledge:kbCategory:add', '新增', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 17:45:06', -1, '2023-05-13 09:55:25', 2);
INSERT INTO `castle_sys_menu` VALUES (1656959125621571586, 1656956377199017986, 'knowledge:kbCategory:delete', '删除', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 17:46:55', -1, '2023-05-13 09:57:15', 2);
INSERT INTO `castle_sys_menu` VALUES (1656959196463366145, 1656956377199017986, 'knowledge:kbCategory:edit', '编辑', NULL, '', 3, '', 1, '', 1, -1, '2023-05-12 17:47:12', -1, '2023-05-15 16:52:17', 2);
INSERT INTO `castle_sys_menu` VALUES (1657228636560121857, 1656867473682927617, 'knowledge:kbGroups:save', '新增群组', NULL, '', 3, '', 1, '', 1, -1, '2023-05-13 11:37:51', -1, '2023-06-03 14:57:21', 2);
INSERT INTO `castle_sys_menu` VALUES (1657230147675500545, 1656867473682927617, 'knowledge:kbGroups:delete;knowledge:kbGroups:deleteBatch', '删除群组', NULL, '', 3, '', 2, '', 1, -1, '2023-05-13 11:43:52', -1, '2023-05-13 14:01:37', 2);
INSERT INTO `castle_sys_menu` VALUES (1657261847159607297, 1656867473682927617, 'knowledge:kbGroups:edit;system:sysUser:edit', '编辑', NULL, '', 3, '', 4, '', 1, -1, '2023-05-13 13:49:49', -1, '2023-06-03 14:56:12', 2);
INSERT INTO `castle_sys_menu` VALUES (1657262593817022466, 1656867473682927617, 'knowledge:kbGroupsUser:deleteBatch', '删除成员', NULL, '', 3, '', 1, '', 1, -1, '2023-05-13 13:52:47', -1, '2023-05-13 14:00:59', 2);
INSERT INTO `castle_sys_menu` VALUES (1657262748070940674, 1656867473682927617, 'knowledge:kbGroups:pageList', '搜索', NULL, '', 3, '', 1, '', 1, -1, '2023-05-13 13:53:24', -1, '2023-06-16 16:47:31', 2);
INSERT INTO `castle_sys_menu` VALUES (1657264776037896194, 1656867473682927617, 'knowledge:kbGroupsUser:delete;knowledge:kbGroups:deleteBatch', '删除', NULL, '', 3, '', 1, '', 1, -1, '2023-05-13 14:01:28', -1, '2023-05-13 14:01:51', 2);
INSERT INTO `castle_sys_menu` VALUES (1657266396922482689, 1656904117437968386, 'kb:kbModel:save', '搜索', NULL, '', 3, '', 1, '', 1, -1, '2023-05-13 14:07:54', NULL, NULL, 2);
INSERT INTO `castle_sys_menu` VALUES (1657266741123846146, 1656904117437968386, 'kb:kbModel:info;knowledge:kbModelLabel:pageList', '详情', NULL, '', 3, '', 1, '', 1, -1, '2023-05-13 14:09:16', -1, '2023-06-03 14:39:39', 2);
INSERT INTO `castle_sys_menu` VALUES (1657266929217409026, 1656904117437968386, 'kb:kbModel:edit', '编辑', NULL, '', 3, '', 1, '', 1, -1, '2023-05-13 14:10:01', -1, '2023-06-16 09:22:55', 2);
INSERT INTO `castle_sys_menu` VALUES (1657267065150607361, 1656904117437968386, 'kb:kbModel:delete', '删除', NULL, '', 3, '', 1, '', 1, -1, '2023-05-13 14:10:34', NULL, NULL, 2);
INSERT INTO `castle_sys_menu` VALUES (1658353773288148993, 1656842923851247617, 'knowledge:kbSubjectWarehouse:add', '新增', NULL, '', 3, '', 1, '', 1, -1, '2023-05-16 14:08:45', NULL, NULL, 2);
INSERT INTO `castle_sys_menu` VALUES (1658354412592353281, 1656842923851247617, 'knowledge:kbSubjectWarehouse:delete', '删除', NULL, '', 3, '', 1, '', 1, -1, '2023-05-16 14:11:17', NULL, NULL, 2);
INSERT INTO `castle_sys_menu` VALUES (1658354532121628673, 1656842923851247617, 'knowledge:kbSubjectWarehouse:edit;knowledge:kbSubjectWarehouse:findById', '编辑', NULL, '', 3, '', 1, '', 1, -1, '2023-05-16 14:11:46', -1, '2023-06-05 10:25:09', 2);
INSERT INTO `castle_sys_menu` VALUES (1658354793284161537, 1656842923851247617, 'knowledge:kbWarehouseAuth:edit;knowledge:kbWarehouseAuth:findBySwId', '权限调整', NULL, '', 3, '', 1, '', 1, -1, '2023-05-16 14:12:48', -1, '2023-05-16 15:33:15', 2);
INSERT INTO `castle_sys_menu` VALUES (1664833720341098498, 1656867382108688385, 'system:sysDept:info;system:sysDept:edit', '修改部门', NULL, '', 3, '', 1, '', 1, -1, '2023-06-03 11:17:45', -1, '2023-06-13 15:26:57', 2);
INSERT INTO `castle_sys_menu` VALUES (1664858549890592769, 1656842546896564225, 'knowledge:kbModelLabel:pageList;knowledge:kbModelLabel:info;knowledge:kbLabelCategory:pageList', '知识标签', NULL, 'el-icon-platform-eleme', 1, '/knowledgeLabels', 3, '知识标签', 1, -1, '2023-06-03 12:56:25', -1, '2023-06-03 12:58:53', 2);
INSERT INTO `castle_sys_menu` VALUES (1664862648468086785, 1664858549890592769, 'knowledge:kbModelLabel:edit;knowledge:kbLabelCategory:deleteBatch;knowledge:kbLabelCategory:delete', '置为失效', NULL, '', 3, '', 1, '', 1, -1, '2023-06-03 13:12:42', -1, '2023-06-03 13:13:07', 2);
INSERT INTO `castle_sys_menu` VALUES (1664872673613811714, 1664858549890592769, 'knowledge:kbModelLabel:save;knowledge:kbLabelCategory:save', '新增', NULL, '', 3, '', 1, '', 1, -1, '2023-06-03 13:52:32', -1, '2023-06-03 13:54:05', 2);
INSERT INTO `castle_sys_menu` VALUES (1664893736443715585, 1656867473682927617, 'knowledge:kbGroups:list;knowledge:kbGroups:save;/knowledge/kbGroups/listFind;system:sysDept:list', '新增成员', NULL, '', 3, '', 1, '', 1, -1, '2023-06-03 15:16:14', -1, '2023-06-03 15:25:12', 2);
INSERT INTO `castle_sys_menu` VALUES (1666073238645841922, 1656868013343051778, 'system:sysRole:dataAuth', '数据权限', NULL, '', 3, '', 1, '', 1, -1, '2023-06-06 21:23:09', NULL, NULL, 2);
INSERT INTO `castle_sys_menu` VALUES (1667101719068434434, 1664858549890592769, 'knowledge:kbModelLabel:delete;knowledge:kbLabelCategory:delete', '删除', NULL, '', 3, '', 1, '', 1, -1, '2023-06-09 17:29:58', -1, '2023-06-15 15:29:12', 2);
INSERT INTO `castle_sys_menu` VALUES (1667101852036259842, 1664858549890592769, 'knowledge:kbModelLabel:deleteBatch;knowledge:kbLabelCategory:deleteBatch', '批量删除', NULL, '', 3, '', 1, '', 1, -1, '2023-06-09 17:30:29', -1, '2023-06-15 15:27:06', 2);
INSERT INTO `castle_sys_menu` VALUES (1669627260572913665, 1664858549890592769, 'knowledge:kbLabelCategory:save', '新增分类', NULL, '', 3, '', 1, '', 1, -1, '2023-06-16 16:45:34', -1, '2023-06-16 16:46:02', 2);
INSERT INTO `castle_sys_menu` VALUES (1669630807020441602, 1664858549890592769, 'knowledge:kbModelLabel:edit', '置为生效', NULL, '', 3, '', 1, '', 1, -1, '2023-06-16 16:59:39', -1, '2023-06-16 16:59:59', 2);
INSERT INTO `castle_sys_menu` VALUES (1669631262379249666, 1664858549890592769, '/knowledge/kbLabelCategory/showLabel;knowledge:kbLabelCategory:pageList', '搜索', NULL, '', 3, '', 1, '', 1, -1, '2023-06-16 17:01:28', -1, '2023-06-16 17:04:03', 2);
INSERT INTO `castle_sys_menu` VALUES (1669906154454626305, 1656842546896564225, 'knowledge:kbBanner:info;knowledge:kbBanner:pageList', '首页配图', NULL, 'el-icon-platform-eleme', 1, '/knowledgeBanner', 4, 'banner图管理', 1, -1, '2023-06-17 11:13:47', -1, '2023-06-17 11:17:26', 2);
INSERT INTO `castle_sys_menu` VALUES (1669906277863632898, 1669906154454626305, 'knowledge:kbBanner:save', '新增', NULL, '', 3, '', 1, '', 1, -1, '2023-06-17 11:14:17', -1, '2023-06-17 11:15:35', 2);
INSERT INTO `castle_sys_menu` VALUES (1669906341646413825, 1669906154454626305, 'knowledge:kbBanner:delete', '删除', NULL, '', 3, '', 1, '', 1, -1, '2023-06-17 11:14:32', -1, '2023-06-17 11:16:12', 2);
INSERT INTO `castle_sys_menu` VALUES (1669906393555120130, 1669906154454626305, 'knowledge:kbBanner:edit;knowledge:kbBanner:info', '编辑', NULL, '', 3, '', 1, '', 1, -1, '2023-06-17 11:14:44', -1, '2023-06-17 11:15:43', 2);
INSERT INTO `castle_sys_menu` VALUES (1669906461540593665, 1669906154454626305, 'knowledge:kbBanner:deleteBatch', '批量删除', NULL, '', 3, '', 1, '', 1, -1, '2023-06-17 11:15:00', -1, '2023-06-17 11:16:01', 2);
INSERT INTO `castle_sys_menu` VALUES (1669906548803088385, 1669906154454626305, 'knowledge:kbBanner:info', '详情', NULL, '', 3, '', 1, '', 1, -1, '2023-06-17 11:15:21', -1, '2023-06-17 11:15:51', 2);
INSERT INTO `castle_sys_menu` VALUES (1669911124324761601, 1656842546896564225, '', '通用模块', NULL, 'el-icon-eleme', 1, '/knowledgeBeCurrent', 5, '', 1, -1, '2023-06-17 11:33:32', -1, '2023-06-17 11:35:01', 2);
INSERT INTO `castle_sys_menu` VALUES (1669943933470883842, 1669911124324761601, 'system:protocol:pageList;system:protocol:info', '使用协议', NULL, 'el-icon-platform-eleme', 1, '/knowledgeBeCurrent/agreement', 1, '', 1, -1, '2023-06-17 13:43:54', -1, '2023-06-19 14:25:19', 2);
INSERT INTO `castle_sys_menu` VALUES (1669944158918918146, 1669911124324761601, '', '文件下载', NULL, 'el-icon-platform-eleme', 1, '/knowledgeBeCurrent/download', 2, '', 1, -1, '2023-06-17 13:44:48', -1, '2023-06-17 13:45:43', 2);
INSERT INTO `castle_sys_menu` VALUES (1670678654876663809, 1669943933470883842, 'system:protocol:save', '新增', NULL, '', 3, '', 1, '', 1, -1, '2023-06-19 14:23:26', -1, '2023-06-19 14:25:36', 2);
INSERT INTO `castle_sys_menu` VALUES (1670678747671445506, 1669943933470883842, 'system:protocol:edit', '编辑', NULL, '', 3, '', 1, '', 1, -1, '2023-06-19 14:23:48', -1, '2023-06-19 14:26:08', 2);
INSERT INTO `castle_sys_menu` VALUES (1670678821801574402, 1669943933470883842, 'system:protocol:delete', '删除', NULL, '', 3, '', 1, '', 1, -1, '2023-06-19 14:24:05', -1, '2023-06-19 14:25:50', 2);
INSERT INTO `castle_sys_menu` VALUES (1670678921445654530, 1669943933470883842, 'system:protocol:deleteBatch', '批量删除', NULL, '', 3, '', 1, '', 1, -1, '2023-06-19 14:24:29', -1, '2023-06-19 14:26:23', 2);
INSERT INTO `castle_sys_menu` VALUES (1670963824724897793, 1656867382108688385, 'system:sysUser:setStatus', '置为生效', NULL, '', 3, '', 1, '', 1, -1, '2023-06-20 09:16:35', -1, '2023-06-20 09:17:09', 2);
INSERT INTO `castle_sys_menu` VALUES (1670980059403485185, 1664858549890592769, 'knowledge:kbLabelCategory:edit;knowledge:kbLabelCategory:info;knowledge:kbLabelCategory:pageList;knowledge:kbModelLabel:edit', '编辑分类', NULL, '', 3, '', 3, '标签分类编辑', 1, -1, '2023-06-20 10:21:06', -1, '2023-06-20 10:22:14', 2);
INSERT INTO `castle_sys_menu` VALUES (1672842895206838274, 1669944158918918146, 'knowledge:kbDownloadConf:pageList;', '查看', NULL, '', 3, '', 1, '', 1, -1, '2023-06-25 13:43:21', NULL, NULL, 2);
INSERT INTO `castle_sys_menu` VALUES (1672843067680813058, 1669944158918918146, 'knowledge:kbDownloadConf:edit;knowledge:kbDownloadConf:info;knowledge:kbDownloadConf:pageList;', '编辑', NULL, '', 3, '', 1, '', 1, -1, '2023-06-25 13:44:02', NULL, NULL, 2);
INSERT INTO `castle_sys_menu` VALUES (1672885152270192642, 1669943933470883842, 'system:protocol:info', '详情', NULL, '', 3, '', 1, '', 1, -1, '2023-06-25 16:31:16', -1, '2023-06-25 16:35:07', 2);

-- ----------------------------
-- Table structure for castle_sys_oss_record
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_oss_record`;
CREATE TABLE `castle_sys_oss_record`  (
  `id` bigint NOT NULL COMMENT 'id',
  `resource_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件名',
  `resource_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件地址',
  `oss_platform` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '平台类型',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `user_type` int NULL DEFAULT NULL COMMENT '用户类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'oss上传记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_oss_record
-- ----------------------------
INSERT INTO `castle_sys_oss_record` VALUES (1925033032300470273, 'WeChat.png', 'upload/20250521/1747798614887.png', '1', NULL, '2025-05-21 11:36:55', NULL);

-- ----------------------------
-- Table structure for castle_sys_post
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_post`;
CREATE TABLE `castle_sys_post`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '职位名称',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '职位描述',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '上级职位',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '所属部门',
  `data_auth_type` int NULL DEFAULT NULL COMMENT '数据权限类型参照枚举DataPermissionPostEnum',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes生效；no失效',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1648908196212920322 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '系统职位表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_post
-- ----------------------------
INSERT INTO `castle_sys_post` VALUES (1587349871610679297, '一部主管', NULL, NULL, 1587349375047028738, 1, NULL, -1, '2022-11-01 15:44:36', NULL, NULL, 2);
INSERT INTO `castle_sys_post` VALUES (1587349931111075842, '二部主管', NULL, NULL, 1587349475806793730, 1, NULL, -1, '2022-11-01 15:44:50', NULL, NULL, 2);
INSERT INTO `castle_sys_post` VALUES (1587349968503296002, '三部主管', NULL, NULL, 1587349550989692930, 1, NULL, -1, '2022-11-01 15:44:59', NULL, NULL, 2);
INSERT INTO `castle_sys_post` VALUES (1587350024438534146, '一部组员', NULL, 1587349871610679297, 1587349375047028738, 3, NULL, -1, '2022-11-01 15:45:12', NULL, NULL, 2);
INSERT INTO `castle_sys_post` VALUES (1587350075646791682, '一组主管', NULL, NULL, 1587349605343678465, 1, NULL, -1, '2022-11-01 15:45:24', NULL, NULL, 2);
INSERT INTO `castle_sys_post` VALUES (1587350136808132610, '一组组员', NULL, 1587350192298774530, 1587349605343678465, 3, NULL, -1, '2022-11-01 15:45:39', -1, '2022-11-01 15:46:07', 2);
INSERT INTO `castle_sys_post` VALUES (1587350192298774530, '一组组长', NULL, 1587350075646791682, 1587349605343678465, 2, NULL, -1, '2022-11-01 15:45:52', NULL, NULL, 2);
INSERT INTO `castle_sys_post` VALUES (1587351179629547522, '公司主管', NULL, NULL, 1587349238514044929, 1, NULL, -1, '2022-11-01 15:49:47', NULL, NULL, 2);
INSERT INTO `castle_sys_post` VALUES (1648898794600812546, 'test', NULL, NULL, 1648896133931487234, 1, NULL, -1, '2023-04-20 11:58:02', NULL, NULL, 2);
INSERT INTO `castle_sys_post` VALUES (1648908196212920321, '天天', NULL, NULL, 1648896088104521729, 1, NULL, -1, '2023-04-20 12:35:24', NULL, NULL, 2);

-- ----------------------------
-- Table structure for castle_sys_region
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_region`;
CREATE TABLE `castle_sys_region`  (
  `id` bigint NOT NULL COMMENT 'id',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '上级ID，一级为0',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `tree_level` tinyint NULL DEFAULT NULL COMMENT '层级',
  `leaf` tinyint NULL DEFAULT NULL COMMENT '是否叶子节点  0：否   1：是',
  `sort` bigint NULL DEFAULT NULL COMMENT '排序',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '行政区域' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_region
-- ----------------------------

-- ----------------------------
-- Table structure for castle_sys_role
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_role`;
CREATE TABLE `castle_sys_role`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '角色名称',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '角色的描述',
  `is_admin` int NULL DEFAULT NULL COMMENT '是否超管 YesNoEnum',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes生效；no失效',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1706508753601560579 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_role
-- ----------------------------
INSERT INTO `castle_sys_role` VALUES (1375356926163554305, '超级管理员', '系统超级管理员，勿删,知道了', 2, 1, -1, '2021-03-26 16:00:19', 1375360470249771009, '2023-05-22 17:24:06', 2);
INSERT INTO `castle_sys_role` VALUES (1702581469358342146, '技术部门', '技术部门菜单', 2, 1, 1375360470249771009, '2023-09-15 15:13:49', 1375360470249771009, '2023-09-28 09:33:38', 2);
INSERT INTO `castle_sys_role` VALUES (1706508753601560578, '通用分类管理员', '数据库专用管理员', 2, 1, 1375360470249771009, '2023-09-26 11:19:27', 1375360470249771009, '2023-09-28 11:50:36', 2);

-- ----------------------------
-- Table structure for castle_sys_role_data_auth
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_role_data_auth`;
CREATE TABLE `castle_sys_role_data_auth`  (
  `id` bigint NOT NULL COMMENT '主键',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色编码',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '菜单编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '角色数据权限表-细化到部门' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_role_data_auth
-- ----------------------------
INSERT INTO `castle_sys_role_data_auth` VALUES (1702578120982544385, 1375356926163554305, 1587349238514044929);

-- ----------------------------
-- Table structure for castle_sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_role_menu`;
CREATE TABLE `castle_sys_role_menu`  (
  `id` bigint NOT NULL COMMENT '主键',
  `role_id` bigint NULL DEFAULT NULL COMMENT '角色编码',
  `menu_id` bigint NULL DEFAULT NULL COMMENT '菜单编码',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_role_menu
-- ----------------------------
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148545, 1375356926163554305, 1656867256074047489);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148546, 1375356926163554305, 1656842546896564225);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148547, 1375356926163554305, 1656904019786182658);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148548, 1375356926163554305, 1656867382108688385);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148549, 1375356926163554305, 1656867473682927617);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148550, 1375356926163554305, 1656868013343051778);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148551, 1375356926163554305, 1656842923851247617);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148552, 1375356926163554305, 1656956377199017986);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148553, 1375356926163554305, 1664858549890592769);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148554, 1375356926163554305, 1669906154454626305);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148555, 1375356926163554305, 1669911124324761601);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148556, 1375356926163554305, 1656904117437968386);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148557, 1375356926163554305, 1656868824257204225);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148558, 1375356926163554305, 1656868500343689217);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148559, 1375356926163554305, 1670963824724897793);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148560, 1375356926163554305, 1656949023917211650);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148561, 1375356926163554305, 1656868611425636354);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148562, 1375356926163554305, 1656868672599560194);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148563, 1375356926163554305, 1656868420572221442);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148564, 1375356926163554305, 1664833720341098498);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148565, 1375356926163554305, 1664893736443715585);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148566, 1375356926163554305, 1657228636560121857);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148567, 1375356926163554305, 1657262593817022466);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148568, 1375356926163554305, 1657262748070940674);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148569, 1375356926163554305, 1657264776037896194);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148570, 1375356926163554305, 1657230147675500545);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148571, 1375356926163554305, 1657261847159607297);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148572, 1375356926163554305, 1656954394169835521);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148573, 1375356926163554305, 1656954456983732225);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148574, 1375356926163554305, 1656954609102749698);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148575, 1375356926163554305, 1656954571421122562);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148576, 1375356926163554305, 1666073238645841922);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148577, 1375356926163554305, 1658354412592353281);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148578, 1375356926163554305, 1658354793284161537);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148579, 1375356926163554305, 1658354532121628673);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148580, 1375356926163554305, 1658353773288148993);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148581, 1375356926163554305, 1656959196463366145);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148582, 1375356926163554305, 1656958667213504513);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148583, 1375356926163554305, 1656959125621571586);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148584, 1375356926163554305, 1664862648468086785);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148585, 1375356926163554305, 1669627260572913665);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148586, 1375356926163554305, 1667101852036259842);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148587, 1375356926163554305, 1667101719068434434);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148588, 1375356926163554305, 1669631262379249666);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148589, 1375356926163554305, 1664872673613811714);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148590, 1375356926163554305, 1669630807020441602);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148591, 1375356926163554305, 1670980059403485185);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148592, 1375356926163554305, 1669906341646413825);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148593, 1375356926163554305, 1669906461540593665);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148594, 1375356926163554305, 1669906548803088385);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148595, 1375356926163554305, 1669906393555120130);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148596, 1375356926163554305, 1669906277863632898);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148597, 1375356926163554305, 1669943933470883842);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148598, 1375356926163554305, 1669944158918918146);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148599, 1375356926163554305, 1657267065150607361);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148600, 1375356926163554305, 1657266396922482689);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148601, 1375356926163554305, 1656947862212444162);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148602, 1375356926163554305, 1657266929217409026);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148603, 1375356926163554305, 1657266741123846146);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148604, 1375356926163554305, 1670678654876663809);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148605, 1375356926163554305, 1672885152270192642);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148606, 1375356926163554305, 1670678821801574402);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148607, 1375356926163554305, 1670678921445654530);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148608, 1375356926163554305, 1670678747671445506);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148609, 1375356926163554305, 1672843067680813058);
INSERT INTO `castle_sys_role_menu` VALUES (1702579426103148610, 1375356926163554305, 1672842895206838274);

-- ----------------------------
-- Table structure for castle_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_user`;
CREATE TABLE `castle_sys_user`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `login_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录名',
  `nickname` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `real_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '实名',
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮件',
  `phone` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机电话',
  `gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '性别 f 女 m 男',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `avatar` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `idcard_type` varchar(3) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件类型：身份证 护照 军官证等',
  `idcard` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件号码',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '个性签名',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门编码',
  `post_id` bigint NULL DEFAULT NULL COMMENT '职位编码',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_post` bigint NULL DEFAULT NULL COMMENT '创建职位',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  `union_id` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信unionid',
  `openid` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `wx_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信昵称',
  `wx_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信头像',
  `wx_bind_time` datetime NULL DEFAULT NULL COMMENT '微信绑定时间',
  `qq_openid` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'qq openid',
  `qq_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'qq昵称',
  `qq_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'qq头像',
  `qq_bind_time` datetime NULL DEFAULT NULL COMMENT 'qq头像\r\n',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `office_phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '办公电话',
  PRIMARY KEY (`id`, `gender`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1707241195564183554 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户信息表，保存用户信息。' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_user
-- ----------------------------
INSERT INTO `castle_sys_user` VALUES (1375360470249771009, 'admin', '系统管理员', '超级管理员', '465c194afb65670f38322df087f0a9bb225cc257e43eb4ac5a0c98ef5b3173ac', 'sun@hcses.com', '13455656999', 'm', '2020-07-19', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695718145901.jpg', '', '', '请你不要迷恋哥  哥只是个传说～～～', -1, -1, 1, -1, NULL, NULL, '2021-03-26 16:14:24', 1375360470249771009, '2023-09-28 17:31:51', 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for castle_sys_user_ding
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_user_ding`;
CREATE TABLE `castle_sys_user_ding`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `ding_unionid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '钉钉unionid',
  `ding_userid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '钉钉userid',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `dept_id_list` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成员所属部门id列表',
  `role_list` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '角色列表(角色ID,名称,角色组名称)',
  `mobile` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `org_email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业邮箱。',
  `avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '头像',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1605128940100349956 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户钉钉信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_user_ding
-- ----------------------------
INSERT INTO `castle_sys_user_ding` VALUES (1605128939722862594, 1375360470249771009, 'iPBSDwh1wksD74aeBFHUqegiEiE', '025967122026619321', '曹黎明', '[375629206]', '[{\"group_name\":\"默认\",\"id\":156429717,\"name\":\"子管理员\"}]', '13280159716', '', 'caoliming@hcses.com', 'https://static-legacy.dingtalk.com/media/lADPDhJzvnPW8vDNASDNASA_288_288.jpg');
INSERT INTO `castle_sys_user_ding` VALUES (1605128940100349953, 1597922245961515009, 'AnQv43Ysyh4iiQrIViPxx1cwiEiE', '01093925290038649938', '马俊杰', '[375629206]', '[{\"group_name\":\"岗位\",\"id\":156429729,\"name\":\"员工\"}]', '15966198621', 'majunjie@hcses.com', 'majunjie@hcses.com', '');
INSERT INTO `castle_sys_user_ding` VALUES (1605128940100349954, 1600047267134369794, 'xGbZAyqXQuvHzUKPX95TFgiEiE', '083113236132428003', '翁立巍', '[375629206]', '[{\"group_name\":\"默认\",\"id\":156429717,\"name\":\"子管理员\"},{\"group_name\":\"岗位\",\"id\":156429729,\"name\":\"员工\"}]', '15684314381', '', 'wengliwei@hcses.com', 'https://static-legacy.dingtalk.com/media/lADPDh0cQ4koO5vNAYTNAYQ_388_388.jpg');
INSERT INTO `castle_sys_user_ding` VALUES (1605128940100349955, 1605112772219342850, 'bR5T0vW0Ay4wKriPDgbYoDAiEiE', '106501374729327383', '王海臣', '[375629206]', NULL, '15617783701', 'wanghaichen@hcses.com', 'wanghaichen@hcses.com', 'https://static-legacy.dingtalk.com/media/lADPDg7mRaGk8XLNAyrNAyo_810_810.jpg');

-- ----------------------------
-- Table structure for castle_sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_user_role`;
CREATE TABLE `castle_sys_user_role`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint UNSIGNED NOT NULL COMMENT '用户ID',
  `role_id` bigint UNSIGNED NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户和角色的多对多映射表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_user_role
-- ----------------------------
INSERT INTO `castle_sys_user_role` VALUES (1398191417231929346, 1375360470249771009, 1375356926163554305);
INSERT INTO `castle_sys_user_role` VALUES (1707241431284068354, 1707241195564183553, 1706508753601560578);

-- ----------------------------
-- Table structure for castle_sys_user_we_com
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_user_we_com`;
CREATE TABLE `castle_sys_user_we_com`  (
  `id` bigint UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `we_com_userid` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企微userid',
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '姓名',
  `department` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '成员所属部门id列表',
  `position` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '职务信息',
  `mobile` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `gender` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别 0表示未定义，1表示男性，2表示女性',
  `status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '激活状态 1=已激活，2=已禁用，4=未激活，5=退出企业。',
  `avatar` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '头像',
  `isleader` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '未知意义字段',
  `extattr` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '扩展属性',
  `english_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '英文名',
  `telephone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '座机',
  `enable` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '未知意义字段',
  `hide_mobile` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '未知意义字段',
  `sort` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '部门内的排序值',
  `external_profile` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '成员对外属性',
  `main_department` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '主部门 仅当应用对主部门有查看权限时返回',
  `qr_code` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '员工个人二维码',
  `alias` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '别名',
  `is_leader_in_dept` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表示在所在的部门内是否为部门负责人，数量与department一致',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地址',
  `thumb_avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像缩略图url',
  `direct_leader` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '直属上级UserID',
  `biz_mail` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '企业邮箱',
  `open_userid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '全局唯一id 仅第三方应用可获取',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1610098885468921858 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户企业微信信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_user_we_com
-- ----------------------------
INSERT INTO `castle_sys_user_we_com` VALUES (1610098885338898434, 1375360470249771009, 'caoliming', '曹黎明', '[20,10]', '技术总监', NULL, NULL, NULL, '1', NULL, '[0,1]', '{\"attrs\":[]}', '', '0536-8184803', '1', '0', '[0,0]', '{\"external_attr\":[],\"external_corp_name\":\"\"}', '20', NULL, '', NULL, NULL, NULL, '[\"SunYuWei\"]', NULL, NULL, '2023-01-03 10:21:03');
INSERT INTO `castle_sys_user_we_com` VALUES (1610098885460533250, 1597922245961515009, 'MaJunJie', '马俊杰', '[27,10]', '', NULL, NULL, NULL, '1', NULL, '[0,0]', '{\"attrs\":[]}', '', '', '1', '0', '[0,0]', '{\"external_attr\":[],\"external_corp_name\":\"\"}', '10', NULL, '', NULL, NULL, NULL, '[]', NULL, NULL, '2023-01-03 10:21:03');
INSERT INTO `castle_sys_user_we_com` VALUES (1610098885468921857, 1600047267134369794, 'wengliwei', '翁立巍', '[10]', '软件工程师', NULL, NULL, NULL, '1', NULL, '[1]', '{\"attrs\":[]}', 'Mgg', '0536-8184803', '1', '0', '[0]', '{\"external_attr\":[],\"external_corp_name\":\"\"}', '10', NULL, 'Mgg', NULL, NULL, NULL, '[\"caoliming\"]', NULL, NULL, '2023-01-03 10:21:03');

-- ----------------------------
-- Table structure for castle_sys_view_table_list
-- ----------------------------
DROP TABLE IF EXISTS `castle_sys_view_table_list`;
CREATE TABLE `castle_sys_view_table_list`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `tb_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表名',
  `prop_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性名',
  `prop_desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性注释',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1628226670024529219 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视图列表字段展示配置 业务库与代码生成库都需要该表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_sys_view_table_list
-- ----------------------------

-- ----------------------------
-- Table structure for castle_version
-- ----------------------------
DROP TABLE IF EXISTS `castle_version`;
CREATE TABLE `castle_version`  (
  `id` bigint NOT NULL COMMENT 'id',
  `version` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本号',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '内容',
  `app_url` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT 'app下载地址',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '版本管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of castle_version
-- ----------------------------
INSERT INTO `castle_version` VALUES (1493048777679736833, 'v1.0.0', '首个版本', '初始化版本', 'www.baidu.com', -1, '2022-02-14 10:25:43', -1, '2022-02-14 10:25:46', 2, 1);
INSERT INTO `castle_version` VALUES (1493071090630430721, 'v1.0.1', '新版本', '升级了版本号', '', -1, '2022-02-14 11:54:23', NULL, NULL, 2, 1);

-- ----------------------------
-- Table structure for kb_banner
-- ----------------------------
DROP TABLE IF EXISTS `kb_banner`;
CREATE TABLE `kb_banner`  (
  `id` bigint NOT NULL COMMENT '主键Id',
  `pc_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '背景图',
  `app_image` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'app图片地址',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片标签，分号间隔',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '跳转连接',
  `weight` int NULL DEFAULT NULL COMMENT '权重',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识banner图表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_banner
-- ----------------------------
INSERT INTO `kb_banner` VALUES (1680768344757907458, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1689561372739.png', NULL, NULL, 2, NULL, 1, 1703968272694964226, '2023-09-19 11:08:16');
INSERT INTO `kb_banner` VALUES (1703969330506821633, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695092919628.png', NULL, NULL, 2, NULL, 1, 1703968272694964226, '2023-09-19 11:09:35');
INSERT INTO `kb_banner` VALUES (1706865016457998337, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695783299790.jpg', NULL, NULL, 2, NULL, 10, 1375360470249771009, '2023-09-27 10:56:36');
INSERT INTO `kb_banner` VALUES (1706873311071657985, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695785281268.jpeg', NULL, NULL, 2, NULL, 1, 1375360470249771009, '2023-09-27 16:54:17');
INSERT INTO `kb_banner` VALUES (1706873883388633089, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695785417925.jpeg', NULL, NULL, 2, NULL, 1, 1375360470249771009, '2023-09-27 11:57:55');
INSERT INTO `kb_banner` VALUES (1706880471168016385, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695786988635.jpeg', NULL, NULL, 2, NULL, 1, 1375360470249771009, '2023-09-27 16:54:18');
INSERT INTO `kb_banner` VALUES (1706880626319515649, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695787026173.jpeg', NULL, NULL, 2, NULL, 100, 1375360470249771009, '2023-09-27 11:57:14');
INSERT INTO `kb_banner` VALUES (1706880931765510146, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695787099189.jpeg', NULL, NULL, 2, NULL, 10, 1375360470249771009, '2023-09-27 11:58:29');
INSERT INTO `kb_banner` VALUES (1706955392824942593, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695804851466.png', NULL, NULL, 1, NULL, 101, 1375360470249771009, '2023-09-27 16:54:24');
INSERT INTO `kb_banner` VALUES (1706955564556525570, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695804892115.png', NULL, NULL, 1, NULL, 101, 1375360470249771009, '2023-09-27 16:55:01');
INSERT INTO `kb_banner` VALUES (1706955762984853506, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695804922936.png', NULL, NULL, 2, NULL, 10, 1375360470249771009, '2023-09-27 17:07:43');

-- ----------------------------
-- Table structure for kb_base_label_task
-- ----------------------------
DROP TABLE IF EXISTS `kb_base_label_task`;
CREATE TABLE `kb_base_label_task`  (
  `id` bigint NOT NULL COMMENT '主鍵',
  `l_id` bigint NULL DEFAULT NULL COMMENT '删除标签id',
  `status` int NULL DEFAULT NULL COMMENT '状态: 1：新增，2：正常处理中 3：处理失败',
  `message` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '失败日志',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `task_time` datetime NULL DEFAULT NULL COMMENT '任务处理时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签删除任务表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_base_label_task
-- ----------------------------

-- ----------------------------
-- Table structure for kb_basic
-- ----------------------------
DROP TABLE IF EXISTS `kb_basic`;
CREATE TABLE `kb_basic`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `wh_id` bigint NULL DEFAULT NULL COMMENT '主题仓库分类ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标签',
  `auth` bigint NULL DEFAULT NULL COMMENT '作者',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `pub_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `category_id` bigint NULL DEFAULT NULL COMMENT '知识仓库分类ID',
  `model_id` bigint NULL DEFAULT NULL COMMENT '模型id',
  `exp_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `attachment` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '附件',
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `model_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模型编码',
  `remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序号',
  `word_cloud` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '词云',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `is_deleted` int NULL DEFAULT NULL COMMENT '标记删除',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识基本表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_basic
-- ----------------------------
INSERT INTO `kb_basic` VALUES (1707281199984304129, NULL, '词云生成方法及设备', 1375360470249771009, 1707241015288803330, '2023-09-28 00:00:00', 1707280056801583105, 1707271818831998978, NULL, '[{\"path\":\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695882391916.pdf\",\"name\":\"词云生成方法及设备.pdf\",\"url\":\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695882391916.pdf\",\"md5\":\"e0588ae5a9ccc69570d052851540be00\"}]', NULL, 'ufwbirq', '本公开提供一种词云生成方法及设备，涉及计算机处理技术领域。该方法包括：通过词典中的词语对第一文本进行分词得到第一词语，词典中包括置信度大于或等于第一置信度阈值的第二词语，第二词语是从预设时间周期内生成的第二文本中预先提取得到的，置信度与第二词语在第二文本中的出现频次正相关，且与第二词语中的各个字分别在第二文本中的出现频次负相关；根据第一词语生成第一文本的词云。本公开可以将置信度较高的第二词语添加到', NULL, '[{\"weight\":35.33155,\"word\":\"词语\"},{\"weight\":24.48494,\"word\":\"第一\"},{\"weight\":20.594902,\"word\":\"所述\"},{\"weight\":14.605789,\"word\":\"文本\"},{\"weight\":11.344277,\"word\":\"可以\"},{\"weight\":10.042017,\"word\":\"置信\"},{\"weight\":7.92985,\"word\":\"包括\"},{\"weight\":7.4376364,\"word\":\"出现\"},{\"weight\":7.021255,\"word\":\"实施\"},{\"weight\":6.7291303,\"word\":\"公开\"},{\"weight\":5.6345825,\"word\":\"根据\"},{\"weight\":5.631128,\"word\":\"生成\"},{\"weight\":5.103994,\"word\":\"计算机\"},{\"weight\":4.790526,\"word\":\"des\"},{\"weight\":4.712353,\"word\":\"阈值\"},{\"weight\":4.709503,\"word\":\"确定\"},{\"weight\":4.426224,\"word\":\"预设\"},{\"weight\":4.175067,\"word\":\"词典\"},{\"weight\":4.1049814,\"word\":\"目标\"},{\"weight\":4.038425,\"word\":\"得到\"},{\"weight\":3.7580814,\"word\":\"设备\"},{\"weight\":3.700698,\"word\":\"执行\"},{\"weight\":3.6175303,\"word\":\"处理\"},{\"weight\":3.5362787,\"word\":\"装置\"},{\"weight\":3.409186,\"word\":\"用于\"},{\"weight\":3.4075446,\"word\":\"方法\"},{\"weight\":3.1553192,\"word\":\"进行\"},{\"weight\":3.1267142,\"word\":\"通过\"},{\"weight\":3.1040087,\"word\":\"构成\"},{\"weight\":3.0241933,\"word\":\"云生成\"},{\"weight\":2.9819589,\"word\":\"连接\"},{\"weight\":2.9264264,\"word\":\"信息\"},{\"weight\":2.8837934,\"word\":\"长度\"},{\"weight\":2.750894,\"word\":\"过程\"},{\"weight\":2.7485728,\"word\":\"分词\"},{\"weight\":2.7158666,\"word\":\"存储\"},{\"weight\":2.6265569,\"word\":\"方面\"},{\"weight\":2.603088,\"word\":\"程序\"},{\"weight\":2.6023827,\"word\":\"简称\"},{\"weight\":2.5977259,\"word\":\"大于\"},{\"weight\":2.5742545,\"word\":\"合并\"},{\"weight\":2.5721378,\"word\":\"关键\"},{\"weight\":2.5632615,\"word\":\"等于\"},{\"weight\":2.4740934,\"word\":\"技术\"},{\"weight\":2.458427,\"word\":\"多个\"},{\"weight\":2.4402452,\"word\":\"介质\"},{\"weight\":2.383481,\"word\":\"实现\"},{\"weight\":2.382894,\"word\":\"粒度\"},{\"weight\":2.3572927,\"word\":\"数量\"},{\"weight\":2.3345547,\"word\":\"重要性\"}]', 1, '2023-09-28 14:28:52', 1375360470249771009, 2, '2023-09-28 14:29:39', 1707241195564183553);
INSERT INTO `kb_basic` VALUES (1707283097286107138, NULL, '文档标签的生成方法装置终端和存储介质', 1375360470249771009, 1707241015288803330, '2023-09-28 00:00:00', 1707280056801583105, 1707271818831998978, NULL, '[{\"path\":\"upload/20230928/1695882776800.pdf\",\"name\":\"文档标签的生成方法装置终端和存储介质.pdf\",\"url\":\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695882776800.pdf\",\"md5\":\"2cdd7df76c3c0a8dce575bdff3a5d4b1\"}]', NULL, 'ufwbirq', '本公开提供文档标签的生成方法及装置、终端和存储介质。文档标签的生成方法包括：基于第一文档的内容，为第一文档提供候选标签；响应于对候选标签的第一预设操作，生成与第一文档对应的标签；其中，标签与第二文档相关联，第二文档包括第一文档中的与标签对应的文档内容。在本公开的一些实施例中，标签与第二文档相关联，第二文档包括第一文档中的与标签对应的文档内容，如此，在与标签相关联的第二文档中进行了标签聚类，并且可以', NULL, '[{\"weight\":26.734974,\"word\":\"文档\"},{\"weight\":21.60647,\"word\":\"标签\"},{\"weight\":12.669923,\"word\":\"所述\"},{\"weight\":10.408874,\"word\":\"实施\"},{\"weight\":9.093846,\"word\":\"可以\"},{\"weight\":8.830203,\"word\":\"公开\"},{\"weight\":8.367299,\"word\":\"内容\"},{\"weight\":8.2218275,\"word\":\"包括\"},{\"weight\":8.00625,\"word\":\"第一\"},{\"weight\":5.087309,\"word\":\"装置\"},{\"weight\":4.974699,\"word\":\"生成\"},{\"weight\":4.728306,\"word\":\"候选\"},{\"weight\":4.545255,\"word\":\"提供\"},{\"weight\":4.4160514,\"word\":\"介质\"},{\"weight\":4.4043193,\"word\":\"计算机\"},{\"weight\":4.3508162,\"word\":\"存储\"},{\"weight\":4.2871046,\"word\":\"一些\"},{\"weight\":4.252391,\"word\":\"方法\"},{\"weight\":4.1953344,\"word\":\"执行\"},{\"weight\":4.167979,\"word\":\"程序\"},{\"weight\":3.42139,\"word\":\"用于\"},{\"weight\":3.3578396,\"word\":\"采用\"},{\"weight\":3.3441377,\"word\":\"对应\"},{\"weight\":3.0297613,\"word\":\"关联\"},{\"weight\":2.814396,\"word\":\"多个\"},{\"weight\":2.677764,\"word\":\"操作\"},{\"weight\":2.6109247,\"word\":\"存储器\"},{\"weight\":2.5832086,\"word\":\"可读\"},{\"weight\":2.4808722,\"word\":\"特征\"},{\"weight\":2.3877769,\"word\":\"设备\"},{\"weight\":2.346736,\"word\":\"实现\"},{\"weight\":2.313098,\"word\":\"des\"},{\"weight\":2.3050818,\"word\":\"代码\"},{\"weight\":2.290276,\"word\":\"预设\"},{\"weight\":2.2794857,\"word\":\"描述\"},{\"weight\":2.1305594,\"word\":\"终端\"},{\"weight\":2.032931,\"word\":\"根据\"},{\"weight\":2.027306,\"word\":\"理解\"},{\"weight\":2.0039852,\"word\":\"关键词\"},{\"weight\":1.9435439,\"word\":\"组合\"},{\"weight\":1.9039968,\"word\":\"功能\"},{\"weight\":1.8239717,\"word\":\"显示\"},{\"weight\":1.8133638,\"word\":\"进行\"},{\"weight\":1.7900772,\"word\":\"任何\"},{\"weight\":1.7896068,\"word\":\"限于\"},{\"weight\":1.7693577,\"word\":\"09\"},{\"weight\":1.7615988,\"word\":\"网络\"},{\"weight\":1.7097261,\"word\":\"附图\"},{\"weight\":1.6812135,\"word\":\"通过\"},{\"weight\":1.6392479,\"word\":\"方式\"}]', 1, '2023-09-28 14:36:25', 1375360470249771009, 2, '2023-09-28 16:40:14', 1375360470249771009);
INSERT INTO `kb_basic` VALUES (1707283791124652033, NULL, '一种结构化数据版本管理方法与系统', 1375360470249771009, 1707241015288803330, '2023-09-28 00:00:00', 1707280012912386049, 1707271818831998978, NULL, '[{\"url\":\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883070668.pdf\",\"path\":\"upload/20230928/1695883070668.pdf\",\"name\":\"一种结构化数据版本管理方法与系统.pdf\",\"md5\":\"4646b3622500c30d43e0e8afa9999f2d\"}]', NULL, 'ufwbirq', '1.一种结构化数据版本管理方法与系统，其特征在于：包括以下步骤：S1：建立以ID为头结点的倒排索引，所述倒排索引使用链表结构，每个索引链上的第一个节点为最新版本的数据，后续节点上版本依次降低；S2：根据写入的ID在倒排索引的ID中找到数据ID所在位置；S3：获取数据所在位置中的Version与SHA256；S4：计算当前数据的SHA256，并与版本库中当前最新版本的SHA256进行比较；S5：若相', NULL, '[{\"weight\":21.615118,\"word\":\"数据\"},{\"weight\":6.586863,\"word\":\"发明\"},{\"weight\":5.1249256,\"word\":\"获取\"},{\"weight\":4.9051423,\"word\":\"索引\"},{\"weight\":4.499594,\"word\":\"实施\"},{\"weight\":4.3209743,\"word\":\"系统\"},{\"weight\":4.318112,\"word\":\"id\"},{\"weight\":3.7964182,\"word\":\"模块\"},{\"weight\":3.1639872,\"word\":\"管理\"},{\"weight\":3.0340295,\"word\":\"节点\"},{\"weight\":3.0068836,\"word\":\"最新\"},{\"weight\":2.834,\"word\":\"构化\"},{\"weight\":2.8010163,\"word\":\"方法\"},{\"weight\":2.6248322,\"word\":\"结构\"},{\"weight\":2.6234226,\"word\":\"用于\"},{\"weight\":2.532597,\"word\":\"256\"},{\"weight\":2.4970646,\"word\":\"sha\"},{\"weight\":2.279755,\"word\":\"所在\"},{\"weight\":2.246274,\"word\":\"目标\"},{\"weight\":2.2149887,\"word\":\"所述\"},{\"weight\":2.1580186,\"word\":\"插入\"},{\"weight\":2.1547346,\"word\":\"进行\"},{\"weight\":2.1356468,\"word\":\"技术\"},{\"weight\":2.0833402,\"word\":\"头部\"},{\"weight\":2.0241895,\"word\":\"方案\"},{\"weight\":1.977153,\"word\":\"描述\"},{\"weight\":1.9536307,\"word\":\"位置\"},{\"weight\":1.9488207,\"word\":\"存储\"},{\"weight\":1.9030032,\"word\":\"当前\"},{\"weight\":1.8683317,\"word\":\"写入\"},{\"weight\":1.8361845,\"word\":\"计算\"},{\"weight\":1.8333242,\"word\":\"01\"},{\"weight\":1.8040024,\"word\":\"根据\"},{\"weight\":1.7791945,\"word\":\"des\"},{\"weight\":1.7781048,\"word\":\"生成\"},{\"weight\":1.6757715,\"word\":\"专利\"},{\"weight\":1.6262075,\"word\":\"包括\"},{\"weight\":1.6031476,\"word\":\"访问\"},{\"weight\":1.577166,\"word\":\"附图\"},{\"weight\":1.5593821,\"word\":\"head\"},{\"weight\":1.5372077,\"word\":\"申请\"},{\"weight\":1.5154269,\"word\":\"指针\"},{\"weight\":1.4902498,\"word\":\"移动\"},{\"weight\":1.4666735,\"word\":\"具体\"},{\"weight\":1.4634914,\"word\":\"历史版本\"},{\"weight\":1.4364556,\"word\":\"比较\"},{\"weight\":1.4196066,\"word\":\"作为\"},{\"weight\":1.3901511,\"word\":\"领域\"},{\"weight\":1.3545359,\"word\":\"示例\"},{\"weight\":1.3488784,\"word\":\"代理\"}]', 1, '2023-09-28 14:39:10', 1375360470249771009, 2, NULL, NULL);
INSERT INTO `kb_basic` VALUES (1707284634775347201, NULL, '一种基于AI学习的智能防近视系统和方法', 1375360470249771009, 1707241015288803330, '2023-09-28 00:00:00', 1707280012912386049, 1707271818831998978, NULL, '[{\"url\":\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883176913.pdf\",\"path\":\"upload/20230928/1695883176913.pdf\",\"name\":\"一种基于AI学习的智能防近视系统和方法.pdf\",\"md5\":\"4b9f1655d6d3af906cfa3989ed05345e\"}]', NULL, 'ufwbirq', '一种基于AI学习的智能防近视系统，设有用眼行为监测单元，所述用眼行为监测单元包括距离传感器、加速度传感器；装置本体内还设有接收传感单元发回的用眼数据并进行处理的MCU中央处理器，服务器与MCU中央处理器交互传输数据，服务器学习大量用户发回的用眼数据不断优化算法，无线信号收发器和服务器的路径之间还设有移动终端，移动终端将服务器对用眼数据的分析结果显示给用户并在用户控制下分别与MCU中央处理器和服务器', NULL, '[{\"weight\":11.423909,\"word\":\"用户\"},{\"weight\":8.571974,\"word\":\"数据\"},{\"weight\":6.8602753,\"word\":\"服务器\"},{\"weight\":4.69256,\"word\":\"处理器\"},{\"weight\":4.6030645,\"word\":\"cu\"},{\"weight\":4.5303087,\"word\":\"中央\"},{\"weight\":4.240994,\"word\":\"距离\"},{\"weight\":4.0910926,\"word\":\"近视\"},{\"weight\":3.6011643,\"word\":\"传感器\"},{\"weight\":3.5143175,\"word\":\"所述\"},{\"weight\":3.45444,\"word\":\"发明\"},{\"weight\":3.3814545,\"word\":\"通过\"},{\"weight\":3.136602,\"word\":\"接收\"},{\"weight\":3.0690818,\"word\":\"改善\"},{\"weight\":2.8432074,\"word\":\"系统\"},{\"weight\":2.8058877,\"word\":\"收发\"},{\"weight\":2.7968006,\"word\":\"智能\"},{\"weight\":2.697847,\"word\":\"方式\"},{\"weight\":2.6469316,\"word\":\"实施\"},{\"weight\":2.625915,\"word\":\"设置\"},{\"weight\":2.5467887,\"word\":\"包括\"},{\"weight\":2.529326,\"word\":\"装置\"},{\"weight\":2.4527822,\"word\":\"习惯\"},{\"weight\":2.4480495,\"word\":\"监测\"},{\"weight\":2.4339633,\"word\":\"学习\"},{\"weight\":2.412695,\"word\":\"进行\"},{\"weight\":2.2940187,\"word\":\"信号\"},{\"weight\":2.2820778,\"word\":\"本体\"},{\"weight\":2.2498846,\"word\":\"方法\"},{\"weight\":2.191297,\"word\":\"报警\"},{\"weight\":2.1666017,\"word\":\"情况\"},{\"weight\":2.124905,\"word\":\"技术\"},{\"weight\":2.12017,\"word\":\"行为\"},{\"weight\":2.1171398,\"word\":\"la\"},{\"weight\":2.0654547,\"word\":\"ai\"},{\"weight\":2.0595996,\"word\":\"01\"},{\"weight\":2.0490372,\"word\":\"状态\"},{\"weight\":2.0372796,\"word\":\"之间\"},{\"weight\":2.035997,\"word\":\"算法\"},{\"weight\":2.027245,\"word\":\"存储\"},{\"weight\":2.0110183,\"word\":\"发送\"},{\"weight\":1.9952425,\"word\":\"提醒\"},{\"weight\":1.9014311,\"word\":\"des\"},{\"weight\":1.8767112,\"word\":\"终端\"},{\"weight\":1.861961,\"word\":\"移动\"},{\"weight\":1.8527892,\"word\":\"dr\"},{\"weight\":1.8510423,\"word\":\"专利\"},{\"weight\":1.8492961,\"word\":\"运动\"},{\"weight\":1.8335851,\"word\":\"申请\"},{\"weight\":1.8306297,\"word\":\"参考\"}]', 1, '2023-09-28 14:42:31', 1375360470249771009, 2, NULL, NULL);
INSERT INTO `kb_basic` VALUES (1707285522684338177, NULL, '一种结构化数据版本管理方法与系统', 1375360470249771009, 1707241015288803330, '2023-09-28 00:00:00', 1707280171905867777, 1707271818831998978, NULL, '[{\"url\":\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883473695.pdf\",\"path\":\"upload/20230928/1695883473695.pdf\",\"name\":\"一种结构化数据版本管理方法与系统.pdf\",\"md5\":\"4646b3622500c30d43e0e8afa9999f2d\"}]', NULL, 'ufwbirq', '本发明公开了一种结构化数据版本管理方法，包括以下步骤：建立以ID为头结点的倒排索引；当需要写入数据时，先获取头部版本号head，然后将head+1生成新版本号重新赋予head，再将新版本的数据插入到倒排索引头部；当获取最新数据时，只需要定位到目标ID索引节点，再获取首节点的数据即可；当需要获取历史版本时，则指针向后继续移动，一种结构化数据版本管理系统，包括数据版本储存和索引模块，数据版本生成写入模', NULL, '[{\"weight\":21.615118,\"word\":\"数据\"},{\"weight\":6.586863,\"word\":\"发明\"},{\"weight\":5.1249256,\"word\":\"获取\"},{\"weight\":4.9051423,\"word\":\"索引\"},{\"weight\":4.499594,\"word\":\"实施\"},{\"weight\":4.3209743,\"word\":\"系统\"},{\"weight\":4.318112,\"word\":\"id\"},{\"weight\":3.7964182,\"word\":\"模块\"},{\"weight\":3.1639872,\"word\":\"管理\"},{\"weight\":3.0340295,\"word\":\"节点\"},{\"weight\":3.0068836,\"word\":\"最新\"},{\"weight\":2.834,\"word\":\"构化\"},{\"weight\":2.8010163,\"word\":\"方法\"},{\"weight\":2.6248322,\"word\":\"结构\"},{\"weight\":2.6234226,\"word\":\"用于\"},{\"weight\":2.532597,\"word\":\"256\"},{\"weight\":2.4970646,\"word\":\"sha\"},{\"weight\":2.279755,\"word\":\"所在\"},{\"weight\":2.246274,\"word\":\"目标\"},{\"weight\":2.2149887,\"word\":\"所述\"},{\"weight\":2.1580186,\"word\":\"插入\"},{\"weight\":2.1547346,\"word\":\"进行\"},{\"weight\":2.1356468,\"word\":\"技术\"},{\"weight\":2.0833402,\"word\":\"头部\"},{\"weight\":2.0241895,\"word\":\"方案\"},{\"weight\":1.977153,\"word\":\"描述\"},{\"weight\":1.9536307,\"word\":\"位置\"},{\"weight\":1.9488207,\"word\":\"存储\"},{\"weight\":1.9030032,\"word\":\"当前\"},{\"weight\":1.8683317,\"word\":\"写入\"},{\"weight\":1.8361845,\"word\":\"计算\"},{\"weight\":1.8333242,\"word\":\"01\"},{\"weight\":1.8040024,\"word\":\"根据\"},{\"weight\":1.7791945,\"word\":\"des\"},{\"weight\":1.7781048,\"word\":\"生成\"},{\"weight\":1.6757715,\"word\":\"专利\"},{\"weight\":1.6262075,\"word\":\"包括\"},{\"weight\":1.6031476,\"word\":\"访问\"},{\"weight\":1.577166,\"word\":\"附图\"},{\"weight\":1.5593821,\"word\":\"head\"},{\"weight\":1.5372077,\"word\":\"申请\"},{\"weight\":1.5154269,\"word\":\"指针\"},{\"weight\":1.4902498,\"word\":\"移动\"},{\"weight\":1.4666735,\"word\":\"具体\"},{\"weight\":1.4634914,\"word\":\"历史版本\"},{\"weight\":1.4364556,\"word\":\"比较\"},{\"weight\":1.4196066,\"word\":\"作为\"},{\"weight\":1.3901511,\"word\":\"领域\"},{\"weight\":1.3545359,\"word\":\"示例\"},{\"weight\":1.3488784,\"word\":\"代理\"}]', 1, '2023-09-28 14:46:03', 1375360470249771009, 2, NULL, NULL);
INSERT INTO `kb_basic` VALUES (1707287099046387714, NULL, '表格数据的管理方法及装置', 1375360470249771009, 1707241015288803330, '2023-09-28 00:00:00', 1707280012912386049, 1707271818831998978, NULL, '[{\"url\":\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883848451.pdf\",\"path\":\"upload/20230928/1695883848451.pdf\",\"name\":\"表格数据的管理方法及装置.pdf\",\"md5\":\"c461cf51fcae58ffbaf9baa23b27f249\"}]', NULL, 'ufwbirq', '本申请公开了一种表格数据的管理方法。该方法包括：接收第一用户在第一终端新增的原始表格数据；根据第一用户在第一终端的第一管理操作，在所述原始表格数据中检索出待定义参数，并在第一终端输出；根据第一用户在第一终端的第二管理操作，在所述待定义参数中确定目标参数；创建基于所述目标参数为实体的多维表格。本申请解决了由于需要大量人为操作、推理、检索等确定目标实体造成的构建知识图谱各方面成本较高的技术问题。', NULL, '[{\"weight\":13.601445,\"word\":\"第一\"},{\"weight\":11.793163,\"word\":\"参数\"},{\"weight\":9.631416,\"word\":\"表格\"},{\"weight\":6.9467854,\"word\":\"终端\"},{\"weight\":6.717878,\"word\":\"目标\"},{\"weight\":6.661627,\"word\":\"管理\"},{\"weight\":6.6615834,\"word\":\"申请\"},{\"weight\":6.602819,\"word\":\"根据\"},{\"weight\":6.3276157,\"word\":\"数据\"},{\"weight\":6.2699647,\"word\":\"查询\"},{\"weight\":6.1633177,\"word\":\"实施\"},{\"weight\":5.32821,\"word\":\"所述\"},{\"weight\":5.026076,\"word\":\"关系\"},{\"weight\":4.910345,\"word\":\"关联\"},{\"weight\":4.824218,\"word\":\"问题\"},{\"weight\":4.7539096,\"word\":\"操作\"},{\"weight\":4.7256365,\"word\":\"可以\"},{\"weight\":4.6730337,\"word\":\"用户\"},{\"weight\":3.3868828,\"word\":\"成本\"},{\"weight\":3.2163405,\"word\":\"人员\"},{\"weight\":3.1236467,\"word\":\"界面\"},{\"weight\":3.0790849,\"word\":\"用于\"},{\"weight\":3.0770369,\"word\":\"原始\"},{\"weight\":3.0178895,\"word\":\"新增\"},{\"weight\":3.016737,\"word\":\"条件\"},{\"weight\":2.9505877,\"word\":\"确定\"},{\"weight\":2.8819256,\"word\":\"手机\"},{\"weight\":2.7302108,\"word\":\"步骤\"},{\"weight\":2.7009478,\"word\":\"待定\"},{\"weight\":2.6307158,\"word\":\"发明\"},{\"weight\":2.5803356,\"word\":\"检索\"},{\"weight\":2.489215,\"word\":\"输出\"},{\"weight\":2.452291,\"word\":\"des\"},{\"weight\":2.3900466,\"word\":\"接收\"},{\"weight\":2.3810377,\"word\":\"模块\"},{\"weight\":2.3540773,\"word\":\"构建\"},{\"weight\":2.3502417,\"word\":\"装置\"},{\"weight\":2.3369894,\"word\":\"知识\"},{\"weight\":2.2776742,\"word\":\"方法\"},{\"weight\":2.2700381,\"word\":\"存在\"},{\"weight\":2.221084,\"word\":\"所示\"},{\"weight\":2.1987848,\"word\":\"dr\"},{\"weight\":2.1810484,\"word\":\"多维\"},{\"weight\":2.1605263,\"word\":\"技术\"},{\"weight\":2.0413785,\"word\":\"图谱\"},{\"weight\":1.9474505,\"word\":\"如图\"},{\"weight\":1.9465675,\"word\":\"实体\"},{\"weight\":1.8811537,\"word\":\"优选\"},{\"weight\":1.8193911,\"word\":\"包括\"},{\"weight\":1.8068186,\"word\":\"需要\"}]', 1, '2023-09-28 14:52:19', 1375360470249771009, 2, NULL, NULL);
INSERT INTO `kb_basic` VALUES (1707290070165499906, NULL, 'ElasticSearch学习文档V2.0', 1375360470249771009, 1707241015288803330, '2023-09-28 00:00:00', 1707244436918120449, 1707242932215754754, NULL, '[{\"url\":\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695884441460.pdf\",\"path\":\"upload/20230928/1695884441460.pdf\",\"name\":\"ElasticSearch学习文档V2.0.pdf\",\"md5\":\"aa1839b2e4c2661df5c3009a6d476382\"}]', NULL, 'xgzokmz', 'Elasticsearch是一个分布式系统、RESTful设计风格的搜索和数据剖析模块，可以处理层出不穷出的各种各样测试用例', NULL, '[{\"weight\":21.96749,\"word\":\"search\"},{\"weight\":15.142114,\"word\":\"query\"},{\"weight\":14.127055,\"word\":\"查询\"},{\"weight\":13.854374,\"word\":\"elastic\"},{\"weight\":10.846004,\"word\":\"product\"},{\"weight\":10.434738,\"word\":\"content\"},{\"weight\":10.354897,\"word\":\"索引\"},{\"weight\":9.5791645,\"word\":\"field\"},{\"weight\":7.8664527,\"word\":\"指定\"},{\"weight\":7.0129304,\"word\":\"term\"},{\"weight\":6.962,\"word\":\"id\"},{\"weight\":6.5299063,\"word\":\"builder\"},{\"weight\":6.288363,\"word\":\"spring\"},{\"weight\":5.8731623,\"word\":\"数据\"},{\"weight\":5.820045,\"word\":\"transport\"},{\"weight\":5.6184077,\"word\":\"文档\"},{\"weight\":5.5099616,\"word\":\"字段\"},{\"weight\":5.4447136,\"word\":\"string\"},{\"weight\":5.2370124,\"word\":\"source\"},{\"weight\":5.1982713,\"word\":\"类型\"},{\"weight\":5.1832395,\"word\":\"可以\"},{\"weight\":5.0385113,\"word\":\"集群\"},{\"weight\":4.770946,\"word\":\"test\"},{\"weight\":4.7289143,\"word\":\"find\"},{\"weight\":4.674613,\"word\":\"index\"},{\"weight\":4.604438,\"word\":\"age\"},{\"weight\":4.4387174,\"word\":\"分片\"},{\"weight\":4.4325256,\"word\":\"返回\"},{\"weight\":4.4226837,\"word\":\"创建\"},{\"weight\":4.4012046,\"word\":\"address\"},{\"weight\":4.375797,\"word\":\"安装\"},{\"weight\":4.3524413,\"word\":\"type\"},{\"weight\":4.27605,\"word\":\"节点\"},{\"weight\":4.2012243,\"word\":\"client\"},{\"weight\":4.163932,\"word\":\"搜索\"},{\"weight\":3.8832536,\"word\":\"system\"},{\"weight\":3.8219566,\"word\":\"get\"},{\"weight\":3.7963734,\"word\":\"new\"},{\"weight\":3.5965128,\"word\":\"boo\"},{\"weight\":3.551558,\"word\":\"public\"},{\"weight\":3.5308352,\"word\":\"emp\"},{\"weight\":3.4830608,\"word\":\"response\"},{\"weight\":3.4647164,\"word\":\"list\"},{\"weight\":3.4018068,\"word\":\"sort\"},{\"weight\":3.3522816,\"word\":\"ems\"},{\"weight\":3.3359222,\"word\":\"set\"},{\"weight\":3.2955375,\"word\":\"print\"},{\"weight\":3.2788477,\"word\":\"hit\"},{\"weight\":3.2501392,\"word\":\"操作\"},{\"weight\":3.2248063,\"word\":\"java\"}]', 1, '2023-09-28 15:04:07', 1375360470249771009, 2, NULL, NULL);
INSERT INTO `kb_basic` VALUES (1925033072968441857, NULL, 'WeChat', 1375360470249771009, 1704432192840900609, '2025-05-21 00:00:00', 1707244436918120449, 1707242932215754754, NULL, '[{\"url\":\"http://nginx/upload/20250521/1747798614887.png\",\"path\":\"upload/20250521/1747798614887.png\",\"name\":\"WeChat.png\",\"md5\":\"fb97da53e4d1170c3af347dda5738eea\"}]', NULL, 'xgzokmz', '1', NULL, NULL, 1, '2025-05-21 11:37:05', 1375360470249771009, 2, NULL, NULL);

-- ----------------------------
-- Table structure for kb_basic_history
-- ----------------------------
DROP TABLE IF EXISTS `kb_basic_history`;
CREATE TABLE `kb_basic_history`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父类id',
  `b_id` bigint NOT NULL COMMENT '知识id',
  `wh_id` bigint NULL DEFAULT NULL COMMENT '主题仓库分类ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标签',
  `auth` bigint NULL DEFAULT NULL COMMENT '作者',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `pub_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `category_id` bigint NULL DEFAULT NULL COMMENT '知识仓库分类ID',
  `model_id` bigint NULL DEFAULT NULL COMMENT '模型id',
  `exp_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `attachment` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '附件',
  `label` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '标签',
  `model_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模型编码',
  `remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序号',
  `word_cloud` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '词云',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `version` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '版本',
  `read_count` bigint NULL DEFAULT NULL COMMENT '读取次数',
  `comments_count` bigint NULL DEFAULT NULL COMMENT '评论次数',
  `download_count` bigint NULL DEFAULT NULL COMMENT '下载次数',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改创建',
  `is_deleted` int NULL DEFAULT NULL COMMENT '标记删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识基本表历史表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_basic_history
-- ----------------------------
INSERT INTO `kb_basic_history` VALUES (1704343526302871553, NULL, 1704336669253365761, NULL, 'Java', 1375360470249771009, 1702580856163680258, '2023-09-20 11:28:27', 1702586217629237249, 1702585162312990721, '2023-09-30 00:00:00', '[{\"url\":\"http://192.168.5.110/upload/20230920/1695180094485.docx\",\"path\":\"upload/20230920/1695180094485.docx\",\"name\":\"java面试.docx\",\"md5\":\"be823b99bb4666f585d337d7166ef89b\"}]', NULL, 'llwulnn', '无', NULL, '[{\"weight\":1.4251761,\"word\":\"7151890053952\"},{\"weight\":1.1881876,\"word\":\"数据\"},{\"weight\":0.9259784,\"word\":\"sm\"},{\"weight\":0.89512205,\"word\":\"en\"},{\"weight\":0.89218575,\"word\":\"进行\"},{\"weight\":0.8816963,\"word\":\"字符串\"},{\"weight\":0.8693128,\"word\":\"打印\"},{\"weight\":0.85578185,\"word\":\"降序\"},{\"weight\":0.8301199,\"word\":\"数字\"},{\"weight\":0.8214151,\"word\":\"排列\"},{\"weight\":0.8134471,\"word\":\"字段\"},{\"weight\":0.7873881,\"word\":\"以下\"},{\"weight\":0.77573436,\"word\":\"对应\"},{\"weight\":0.77511334,\"word\":\"统计\"},{\"weight\":0.71798015,\"word\":\"算法\"},{\"weight\":0.70001614,\"word\":\"字符\"},{\"weight\":0.6903114,\"word\":\"菜单\"},{\"weight\":0.66796786,\"word\":\"数量\"},{\"weight\":0.6488289,\"word\":\"结构\"},{\"weight\":0.6440962,\"word\":\"冒泡\"},{\"weight\":0.63481295,\"word\":\"一行\"},{\"weight\":0.5960636,\"word\":\"udt\"},{\"weight\":0.56632626,\"word\":\"请使用\"},{\"weight\":0.56102276,\"word\":\"对象\"},{\"weight\":0.5586076,\"word\":\"反射\"},{\"weight\":0.5471513,\"word\":\"利用\"},{\"weight\":0.5421821,\"word\":\"复制\"},{\"weight\":0.5133409,\"word\":\"展示\"},{\"weight\":0.48309785,\"word\":\"效果\"},{\"weight\":0.4540046,\"word\":\"转化\"},{\"weight\":0.43256938,\"word\":\"如下\"},{\"weight\":0.31651372,\"word\":\"list\"},{\"weight\":0.31397277,\"word\":\"本题\"},{\"weight\":0.29088524,\"word\":\"7151890054598\"},{\"weight\":0.2611202,\"word\":\"7151890053768\"},{\"weight\":0.20099397,\"word\":\"7151890054697\"},{\"weight\":0.19785199,\"word\":\"7151890054335\"}]', 1, 'V1.0', NULL, NULL, NULL, '2023-09-20 11:55:37', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_basic_history` VALUES (1704382483094941697, NULL, 1704023190201970690, NULL, '住院管理信息系统开发', 1375360470249771009, 1587349238514044929, '2023-09-19 14:42:41', 1703705577445937153, 1703686969336549378, NULL, '[{\"url\":\"http://192.168.5.110/upload/20230919/1695105701654.docx\",\"path\":\"upload/20230919/1695105701654.docx\",\"name\":\"打印随便改版本.docx\",\"md5\":\"5c76255a0fe2dcb6982cb95eab666315\"}]', '住院$$系统$$set$$医院$$信息$$result$$sql$$管理$$患者$$病人', 'ryrkvzb', '住院管理信息系统', NULL, '[{\"weight\":18.322763,\"word\":\"住院\"},{\"weight\":17.362188,\"word\":\"系统\"},{\"weight\":16.32475,\"word\":\"set\"},{\"weight\":16.03649,\"word\":\"医院\"},{\"weight\":12.287088,\"word\":\"信息\"},{\"weight\":11.661375,\"word\":\"result\"},{\"weight\":11.32198,\"word\":\"sql\"},{\"weight\":11.017085,\"word\":\"管理\"},{\"weight\":10.013844,\"word\":\"患者\"},{\"weight\":8.784784,\"word\":\"病人\"},{\"weight\":8.683209,\"word\":\"system\"},{\"weight\":8.564313,\"word\":\"string\"},{\"weight\":7.5822053,\"word\":\"public\"},{\"weight\":7.5237975,\"word\":\"id\"},{\"weight\":7.4517937,\"word\":\"医生\"},{\"weight\":6.9419694,\"word\":\"医嘱\"},{\"weight\":6.8526998,\"word\":\"功能\"},{\"weight\":6.503366,\"word\":\"return\"},{\"weight\":6.127059,\"word\":\"查询\"},{\"weight\":5.850726,\"word\":\"num\"},{\"weight\":5.833543,\"word\":\"print\"},{\"weight\":5.686832,\"word\":\"设计\"},{\"weight\":5.6044364,\"word\":\"get\"},{\"weight\":5.460161,\"word\":\"实现\"},{\"weight\":5.235285,\"word\":\"new\"},{\"weight\":5.1907125,\"word\":\"提供\"},{\"weight\":5.0961566,\"word\":\"病历\"},{\"weight\":4.9203253,\"word\":\"护士\"},{\"weight\":4.9000235,\"word\":\"null\"},{\"weight\":4.6658626,\"word\":\"rs\"},{\"weight\":4.6551065,\"word\":\"开发\"},{\"weight\":4.5437746,\"word\":\"费用\"},{\"weight\":4.359514,\"word\":\"进行\"},{\"weight\":4.3517327,\"word\":\"list\"},{\"weight\":4.3494697,\"word\":\"array\"},{\"weight\":4.235155,\"word\":\"药品\"},{\"weight\":4.0869355,\"word\":\"数据库\"},{\"weight\":3.8928785,\"word\":\"数据\"},{\"weight\":3.8634305,\"word\":\"支持\"},{\"weight\":3.819293,\"word\":\"需求\"},{\"weight\":3.7189393,\"word\":\"病案\"},{\"weight\":3.696291,\"word\":\"hp\"},{\"weight\":3.6918688,\"word\":\"记录\"},{\"weight\":3.6884823,\"word\":\"exception\"},{\"weight\":3.6661406,\"word\":\"流程\"},{\"weight\":3.5490212,\"word\":\"用户\"},{\"weight\":3.4889193,\"word\":\"execute\"},{\"weight\":3.4787197,\"word\":\"image\"},{\"weight\":3.427042,\"word\":\"png\"},{\"weight\":3.3537674,\"word\":\"connection\"}]', 1, 'V1.0', 6, 0, NULL, '2023-09-20 14:30:25', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_basic_history` VALUES (1704382952890544129, 1704383073678110721, 1704341941728374785, NULL, 'AAAAAAAAAAAAA', 1375360470249771009, 1702580953790300161, '2023-09-20 11:49:24', 1703705649080455170, 1703686969336549378, '2023-09-30 00:00:00', '[{\"url\":\"http://192.168.5.110/upload/20230920/1695181667041.pptx\",\"path\":\"upload/20230920/1695181667041.pptx\",\"name\":\"castlefortress框架宣传资料源文件.pptx\",\"md5\":\"e980d913ea092580690ad098a8581a1f\"}]', '', 'ryrkvzb', '🈚️', NULL, '[{\"weight\":12.184242,\"word\":\"image\"},{\"weight\":10.156139,\"word\":\"png\"},{\"weight\":8.083686,\"word\":\"企业\"},{\"weight\":6.1038404,\"word\":\"框架\"},{\"weight\":5.734701,\"word\":\"客户\"},{\"weight\":5.3240857,\"word\":\"业务\"},{\"weight\":5.2910485,\"word\":\"开发\"},{\"weight\":5.0531917,\"word\":\"技术\"},{\"weight\":5.0512743,\"word\":\"可以\"},{\"weight\":4.8167763,\"word\":\"公司\"},{\"weight\":4.6138344,\"word\":\"用户\"},{\"weight\":4.420471,\"word\":\"产品\"},{\"weight\":4.3893323,\"word\":\"服务\"},{\"weight\":3.887393,\"word\":\"能力\"},{\"weight\":3.3404732,\"word\":\"设计\"},{\"weight\":3.301146,\"word\":\"团队\"},{\"weight\":3.1345825,\"word\":\"经验\"},{\"weight\":3.1010728,\"word\":\"平台\"},{\"weight\":3.068756,\"word\":\"进行\"},{\"weight\":2.9886432,\"word\":\"自己\"},{\"weight\":2.8519545,\"word\":\"需求\"},{\"weight\":2.850772,\"word\":\"风格\"},{\"weight\":2.8218179,\"word\":\"信息\"},{\"weight\":2.6943378,\"word\":\"核心\"},{\"weight\":2.6902328,\"word\":\"提升\"},{\"weight\":2.6443138,\"word\":\"api\"},{\"weight\":2.6083322,\"word\":\"相关\"},{\"weight\":2.535822,\"word\":\"jpeg\"},{\"weight\":2.5305305,\"word\":\"软件\"},{\"weight\":2.4796743,\"word\":\"信息化\"},{\"weight\":2.4610057,\"word\":\"行业\"},{\"weight\":2.417416,\"word\":\"数据\"},{\"weight\":2.39159,\"word\":\"完善\"},{\"weight\":2.3911805,\"word\":\"颜色\"},{\"weight\":2.3436627,\"word\":\"体验\"},{\"weight\":2.323022,\"word\":\"智能\"},{\"weight\":2.2698364,\"word\":\"修改\"},{\"weight\":2.1790519,\"word\":\"识别\"},{\"weight\":2.1466694,\"word\":\"ui\"},{\"weight\":2.083022,\"word\":\"交付\"},{\"weight\":2.0758643,\"word\":\"系统\"},{\"weight\":2.0445385,\"word\":\"组件\"},{\"weight\":2.0236926,\"word\":\"界面\"},{\"weight\":1.9724916,\"word\":\"通过\"},{\"weight\":1.941676,\"word\":\"帮助\"},{\"weight\":1.9160938,\"word\":\"微信\"},{\"weight\":1.8806717,\"word\":\"大量\"},{\"weight\":1.8235729,\"word\":\"层级\"},{\"weight\":1.7423054,\"word\":\"分析\"},{\"weight\":1.7372812,\"word\":\"功能\"}]', 1, 'V1.0', 4, 0, NULL, '2023-09-20 14:32:17', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_basic_history` VALUES (1704383073678110721, 1704389348252049409, 1704341941728374785, NULL, 'AAAAAAAAAAAAA', 1375360470249771009, 1702580953790300161, '2023-09-20 11:49:24', 1703705649080455170, 1703686969336549378, '2023-09-30 00:00:00', '[{\"url\":\"http://192.168.5.110/upload/20230920/1695191534508.txt\",\"path\":\"upload/20230920/1695191534508.txt\",\"name\":\"target_file.txt\",\"md5\":\"3744d017b6b931033d3910acb071da0c\"}]', 'Java$$SAA$$1人x s d c s ch ', 'ryrkvzb', '🈚️', NULL, '[{\"weight\":196.36336,\"word\":\"pdf\"},{\"weight\":117.820885,\"word\":\"施工\"},{\"weight\":94.92596,\"word\":\"技术\"},{\"weight\":38.70965,\"word\":\"应用\"},{\"weight\":32.986786,\"word\":\"隧道\"},{\"weight\":31.611668,\"word\":\"材料\"},{\"weight\":30.911884,\"word\":\"申报\"},{\"weight\":29.616127,\"word\":\"论文\"},{\"weight\":27.269516,\"word\":\"二等奖\"},{\"weight\":25.845947,\"word\":\"研究\"},{\"weight\":24.768507,\"word\":\"集团公司\"},{\"weight\":19.453257,\"word\":\"铁路\"},{\"weight\":19.151724,\"word\":\"do\"},{\"weight\":17.563892,\"word\":\"关键\"},{\"weight\":17.476898,\"word\":\"证书\"},{\"weight\":17.01223,\"word\":\"控制\"},{\"weight\":13.455418,\"word\":\"混凝土\"},{\"weight\":12.621106,\"word\":\"连续\"},{\"weight\":11.236408,\"word\":\"地铁\"},{\"weight\":10.835562,\"word\":\"分析\"},{\"weight\":10.327856,\"word\":\"工程\"},{\"weight\":10.307772,\"word\":\"装置\"},{\"weight\":9.887585,\"word\":\"设计\"},{\"weight\":9.7430315,\"word\":\"综合\"},{\"weight\":8.810099,\"word\":\"复杂\"},{\"weight\":8.602597,\"word\":\"浅谈\"},{\"weight\":8.467354,\"word\":\"基坑\"},{\"weight\":8.335709,\"word\":\"地质\"},{\"weight\":8.250113,\"word\":\"轨道\"},{\"weight\":8.197699,\"word\":\"结构\"},{\"weight\":8.096806,\"word\":\"地层\"},{\"weight\":8.059636,\"word\":\"条件下\"},{\"weight\":7.9271245,\"word\":\"直径\"},{\"weight\":7.784194,\"word\":\"箱梁\"},{\"weight\":7.782757,\"word\":\"跨度\"},{\"weight\":7.5424337,\"word\":\"工艺\"},{\"weight\":7.3662157,\"word\":\"既有\"},{\"weight\":7.193105,\"word\":\"zs\"},{\"weight\":7.0132403,\"word\":\"处理\"},{\"weight\":6.926133,\"word\":\"安装\"},{\"weight\":6.5873756,\"word\":\"预制\"},{\"weight\":6.4840035,\"word\":\"系统\"},{\"weight\":6.3109713,\"word\":\"路基\"},{\"weight\":6.2814016,\"word\":\"车站\"},{\"weight\":6.252586,\"word\":\"泥水\"},{\"weight\":6.21246,\"word\":\"桥梁\"},{\"weight\":6.152092,\"word\":\"桁梁\"},{\"weight\":5.9957957,\"word\":\"大桥\"},{\"weight\":5.9177647,\"word\":\"地区\"},{\"weight\":5.823663,\"word\":\"支架\"}]', 1, 'V2.0', 6, 0, NULL, '2023-09-20 14:32:45', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_basic_history` VALUES (1704389348252049409, NULL, 1704341941728374785, NULL, 'AAAAAAAAAAAAA', 1375360470249771009, 1702580953790300161, '2023-09-20 11:49:24', 1703705649080455170, 1703686969336549378, '2023-09-30 00:00:00', '[{\"url\":\"http://192.168.5.110/upload/20230920/1695191562959.xml\",\"path\":\"upload/20230920/1695191562959.xml\",\"name\":\"data.xml\",\"md5\":\"bf5a6650949ba3054e37e6590cf5f66b\"}]', '[\"Java\",\"SAA\",\"1人x s d c s ch \"]', 'ryrkvzb', '🈚️', NULL, '[{\"weight\":196.36336,\"word\":\"pdf\"},{\"weight\":117.820885,\"word\":\"施工\"},{\"weight\":94.92596,\"word\":\"技术\"},{\"weight\":38.70965,\"word\":\"应用\"},{\"weight\":32.986786,\"word\":\"隧道\"},{\"weight\":31.611668,\"word\":\"材料\"},{\"weight\":30.911884,\"word\":\"申报\"},{\"weight\":29.616127,\"word\":\"论文\"},{\"weight\":27.269516,\"word\":\"二等奖\"},{\"weight\":25.845947,\"word\":\"研究\"},{\"weight\":24.768507,\"word\":\"集团公司\"},{\"weight\":19.453257,\"word\":\"铁路\"},{\"weight\":19.151724,\"word\":\"do\"},{\"weight\":17.563892,\"word\":\"关键\"},{\"weight\":17.476898,\"word\":\"证书\"},{\"weight\":17.01223,\"word\":\"控制\"},{\"weight\":13.455418,\"word\":\"混凝土\"},{\"weight\":12.621106,\"word\":\"连续\"},{\"weight\":11.236408,\"word\":\"地铁\"},{\"weight\":10.835562,\"word\":\"分析\"},{\"weight\":10.327856,\"word\":\"工程\"},{\"weight\":10.307772,\"word\":\"装置\"},{\"weight\":9.887585,\"word\":\"设计\"},{\"weight\":9.7430315,\"word\":\"综合\"},{\"weight\":8.810099,\"word\":\"复杂\"},{\"weight\":8.602597,\"word\":\"浅谈\"},{\"weight\":8.467354,\"word\":\"基坑\"},{\"weight\":8.335709,\"word\":\"地质\"},{\"weight\":8.250113,\"word\":\"轨道\"},{\"weight\":8.197699,\"word\":\"结构\"},{\"weight\":8.096806,\"word\":\"地层\"},{\"weight\":8.059636,\"word\":\"条件下\"},{\"weight\":7.9271245,\"word\":\"直径\"},{\"weight\":7.784194,\"word\":\"箱梁\"},{\"weight\":7.782757,\"word\":\"跨度\"},{\"weight\":7.5424337,\"word\":\"工艺\"},{\"weight\":7.3662157,\"word\":\"既有\"},{\"weight\":7.193105,\"word\":\"zs\"},{\"weight\":7.0132403,\"word\":\"处理\"},{\"weight\":6.926133,\"word\":\"安装\"},{\"weight\":6.5873756,\"word\":\"预制\"},{\"weight\":6.4840035,\"word\":\"系统\"},{\"weight\":6.3109713,\"word\":\"路基\"},{\"weight\":6.2814016,\"word\":\"车站\"},{\"weight\":6.252586,\"word\":\"泥水\"},{\"weight\":6.21246,\"word\":\"桥梁\"},{\"weight\":6.152092,\"word\":\"桁梁\"},{\"weight\":5.9957957,\"word\":\"大桥\"},{\"weight\":5.9177647,\"word\":\"地区\"},{\"weight\":5.823663,\"word\":\"支架\"}]', 1, 'V3.0', 10, 0, NULL, '2023-09-20 14:57:41', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_basic_history` VALUES (1704393495361736705, NULL, 1704370222225723394, NULL, '测试标签', 1375360470249771009, 1702581010535038977, '2023-09-20 13:41:41', 1702586217629237249, 1702585162312990721, NULL, '[{\"path\":\"upload/20230920/1695188490150.xml\",\"name\":\"data.xml\",\"url\":\"http://192.168.5.110/upload/20230920/1695188490150.xml\",\"md5\":\"bf5a6650949ba3054e37e6590cf5f66b\"}]', '[\"Java\",\"Java\",\"Java\"]', 'llwulnn', '12', NULL, NULL, 1, 'V1.0', 8, 0, NULL, '2023-09-20 15:14:10', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_basic_history` VALUES (1704396882572484609, NULL, 1703706786961575938, NULL, '机器学习的', 1375360470249771009, 1587349238514044929, '2023-09-18 17:45:26', 1703705577445937153, 1703686969336549378, NULL, '[{\"path\":\"upload/20230918/1695030122842.pptx\",\"name\":\"castlefortress框架宣传资料源文件.pptx\",\"url\":\"http://192.168.5.110/upload/20230918/1695030122842.pptx\",\"md5\":\"e980d913ea092580690ad098a8581a1f\"}]', '[\"大数据\",\"学习\"]', 'ryrkvzb', '机器学习是一门多领域交叉学科，涉及概率论、统计学、逼近论、凸分析、算法复杂度理论等多门学科。专门研究计算机怎样模拟或实现人类的学习行为，以获取新的知识或技能，重新组织已有的知识结构使之不断改善自身的性能。\n它是人工智能核心，是使计算机具有智能的根本途径。', NULL, '[{\"weight\":12.184242,\"word\":\"image\"},{\"weight\":10.156139,\"word\":\"png\"},{\"weight\":8.083686,\"word\":\"企业\"},{\"weight\":6.1038404,\"word\":\"框架\"},{\"weight\":5.734701,\"word\":\"客户\"},{\"weight\":5.3240857,\"word\":\"业务\"},{\"weight\":5.2910485,\"word\":\"开发\"},{\"weight\":5.0531917,\"word\":\"技术\"},{\"weight\":5.0512743,\"word\":\"可以\"},{\"weight\":4.8167763,\"word\":\"公司\"},{\"weight\":4.6138344,\"word\":\"用户\"},{\"weight\":4.420471,\"word\":\"产品\"},{\"weight\":4.3893323,\"word\":\"服务\"},{\"weight\":3.887393,\"word\":\"能力\"},{\"weight\":3.3404732,\"word\":\"设计\"},{\"weight\":3.301146,\"word\":\"团队\"},{\"weight\":3.1345825,\"word\":\"经验\"},{\"weight\":3.1010728,\"word\":\"平台\"},{\"weight\":3.068756,\"word\":\"进行\"},{\"weight\":2.9886432,\"word\":\"自己\"},{\"weight\":2.8519545,\"word\":\"需求\"},{\"weight\":2.850772,\"word\":\"风格\"},{\"weight\":2.8218179,\"word\":\"信息\"},{\"weight\":2.6943378,\"word\":\"核心\"},{\"weight\":2.6902328,\"word\":\"提升\"},{\"weight\":2.6443138,\"word\":\"api\"},{\"weight\":2.6083322,\"word\":\"相关\"},{\"weight\":2.535822,\"word\":\"jpeg\"},{\"weight\":2.5305305,\"word\":\"软件\"},{\"weight\":2.4796743,\"word\":\"信息化\"},{\"weight\":2.4610057,\"word\":\"行业\"},{\"weight\":2.417416,\"word\":\"数据\"},{\"weight\":2.39159,\"word\":\"完善\"},{\"weight\":2.3911805,\"word\":\"颜色\"},{\"weight\":2.3436627,\"word\":\"体验\"},{\"weight\":2.323022,\"word\":\"智能\"},{\"weight\":2.2698364,\"word\":\"修改\"},{\"weight\":2.1790519,\"word\":\"识别\"},{\"weight\":2.1466694,\"word\":\"ui\"},{\"weight\":2.083022,\"word\":\"交付\"},{\"weight\":2.0758643,\"word\":\"系统\"},{\"weight\":2.0445385,\"word\":\"组件\"},{\"weight\":2.0236926,\"word\":\"界面\"},{\"weight\":1.9724916,\"word\":\"通过\"},{\"weight\":1.941676,\"word\":\"帮助\"},{\"weight\":1.9160938,\"word\":\"微信\"},{\"weight\":1.8806717,\"word\":\"大量\"},{\"weight\":1.8235729,\"word\":\"层级\"},{\"weight\":1.7423054,\"word\":\"分析\"},{\"weight\":1.7372812,\"word\":\"功能\"}]', 1, 'V1.0', 13, 0, NULL, '2023-09-20 15:27:38', 1375360470249771009, NULL, NULL, 2);

-- ----------------------------
-- Table structure for kb_basic_label
-- ----------------------------
DROP TABLE IF EXISTS `kb_basic_label`;
CREATE TABLE `kb_basic_label`  (
  `id` bigint NOT NULL COMMENT '主键',
  `b_id` bigint NULL DEFAULT NULL COMMENT '知识id',
  `l_id` bigint NULL DEFAULT NULL COMMENT '标签id',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YES OR NO',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识与标签的中间表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_basic_label
-- ----------------------------

-- ----------------------------
-- Table structure for kb_basic_log
-- ----------------------------
DROP TABLE IF EXISTS `kb_basic_log`;
CREATE TABLE `kb_basic_log`  (
  `id` bigint NOT NULL COMMENT '主键Id',
  `basic_id` bigint NOT NULL COMMENT '知识Id',
  `old_category` bigint NOT NULL COMMENT '旧分类Id',
  `new_category` bigint NOT NULL COMMENT '新分类Id',
  `create_user` bigint NOT NULL COMMENT '创建人Id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `type` int NOT NULL COMMENT '1: 普通知识  2:视频知识',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识移动日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_basic_log
-- ----------------------------
INSERT INTO `kb_basic_log` VALUES (1664545241555828737, 1663101334359994369, 1656837871229014018, 1663027837579399169, 1375360470249771009, '2023-06-02 16:11:26', 1);
INSERT INTO `kb_basic_log` VALUES (1703695335999950849, 1703645345017585665, 1703613908948213762, 1702586184771059713, 1375360470249771009, '2023-09-18 16:59:56', 1);
INSERT INTO `kb_basic_log` VALUES (1706189243237568513, 1703619777899356161, 1702585807652798466, 1702586184771059713, 1706134440184672257, '2023-09-25 14:09:50', 1);

-- ----------------------------
-- Table structure for kb_basic_tags
-- ----------------------------
DROP TABLE IF EXISTS `kb_basic_tags`;
CREATE TABLE `kb_basic_tags`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `basic_id` bigint NULL DEFAULT NULL COMMENT '知识ID',
  `tag_id` bigint NULL DEFAULT NULL COMMENT '标签ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_basic_tags
-- ----------------------------

-- ----------------------------
-- Table structure for kb_basic_trash
-- ----------------------------
DROP TABLE IF EXISTS `kb_basic_trash`;
CREATE TABLE `kb_basic_trash`  (
  `id` bigint NOT NULL COMMENT '主键Id',
  `basic_id` bigint NOT NULL COMMENT '知识Id',
  `user_id` bigint NOT NULL COMMENT '用户Id',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  `type` int NOT NULL COMMENT '1:普通知识库  2：视频知识库',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识回收表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_basic_trash
-- ----------------------------

-- ----------------------------
-- Table structure for kb_basic_user
-- ----------------------------
DROP TABLE IF EXISTS `kb_basic_user`;
CREATE TABLE `kb_basic_user`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NULL DEFAULT NULL COMMENT '当前用户Id',
  `b_id` bigint NULL DEFAULT NULL COMMENT '基础信息Id',
  `type` int NULL DEFAULT NULL COMMENT '1:浏览 2：上传 3：下载',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `attachment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件类型后缀',
  `status` int NULL DEFAULT NULL COMMENT '状态 1：普通知识库  2：视频知识库',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识浏览收藏评论' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_basic_user
-- ----------------------------
INSERT INTO `kb_basic_user` VALUES (1711310074187964417, 1375360470249771009, 1707290070165499906, 2, '2023-10-09 17:18:11', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1711310111739568129, 1375360470249771009, 1707281199984304129, 2, '2023-10-09 17:18:20', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1711310143788244994, 1375360470249771009, 1707283097286107138, 2, '2023-10-09 17:18:28', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1711310172443729921, 1375360470249771009, 1707283791124652033, 2, '2023-10-09 17:18:34', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1711310202315563009, 1375360470249771009, 1707284634775347201, 2, '2023-10-09 17:18:42', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1711310228601266177, 1375360470249771009, 1707285522684338177, 2, '2023-10-09 17:18:48', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1711310257277722625, 1375360470249771009, 1707287099046387714, 2, '2023-10-09 17:18:55', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1711310293759778817, 1375360470249771009, 1707324994151014401, 2, '2023-10-09 17:19:03', NULL, 2);
INSERT INTO `kb_basic_user` VALUES (1711310312969691138, 1375360470249771009, 1707324544685203458, 2, '2023-10-09 17:19:08', NULL, 2);
INSERT INTO `kb_basic_user` VALUES (1711310331789533186, 1375360470249771009, 1707324288421617666, 2, '2023-10-09 17:19:12', NULL, 2);
INSERT INTO `kb_basic_user` VALUES (1711310350861033474, 1375360470249771009, 1707324117012996098, 2, '2023-10-09 17:19:17', NULL, 2);
INSERT INTO `kb_basic_user` VALUES (1711310369253056513, 1375360470249771009, 1707323867078615041, 2, '2023-10-09 17:19:21', NULL, 2);
INSERT INTO `kb_basic_user` VALUES (1711310386617475074, 1375360470249771009, 1707315918516019201, 2, '2023-10-09 17:19:25', NULL, 2);
INSERT INTO `kb_basic_user` VALUES (1711310403889618946, 1375360470249771009, 1707307406977163266, 2, '2023-10-09 17:19:30', NULL, 2);
INSERT INTO `kb_basic_user` VALUES (1925015489728847873, 1375360470249771009, 1707290070165499906, 1, '2025-05-21 10:27:12', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925021300274339842, 1375360470249771009, 1707290070165499906, 1, '2025-05-21 10:50:18', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925021388786737154, 1375360470249771009, 1707290070165499906, 1, '2025-05-21 10:50:39', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925021922709053441, 1375360470249771009, 1707290070165499906, 1, '2025-05-21 10:52:46', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925032708374372353, 1375360470249771009, 1707290070165499906, 1, '2025-05-21 11:35:38', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925033073324957698, 1375360470249771009, 1925033072968441857, 2, '2025-05-21 11:37:05', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925033081218637825, 1375360470249771009, 1925033072968441857, 1, '2025-05-21 11:37:07', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925033227339800578, 1375360470249771009, 1707281199984304129, 1, '2025-05-21 11:37:41', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925034302188920834, 1375360470249771009, 1707281199984304129, 1, '2025-05-21 11:41:58', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925039191279378434, 1375360470249771009, 1707281199984304129, 1, '2025-05-21 12:01:23', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925039351434682370, 1375360470249771009, 1707281199984304129, 1, '2025-05-21 12:02:02', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925078314132271105, 1375360470249771009, 1707281199984304129, 1, '2025-05-21 14:36:51', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925078389747183617, 1375360470249771009, 1925033072968441857, 1, '2025-05-21 14:37:09', NULL, 1);
INSERT INTO `kb_basic_user` VALUES (1925079291224748033, 1375360470249771009, 1707290070165499906, 1, '2025-05-21 14:40:44', NULL, 1);

-- ----------------------------
-- Table structure for kb_category
-- ----------------------------
DROP TABLE IF EXISTS `kb_category`;
CREATE TABLE `kb_category`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '分类名称',
  `sw_id` bigint NULL DEFAULT NULL COMMENT '知识仓库id',
  `sort` int NULL DEFAULT NULL COMMENT '排序号',
  `status` int NOT NULL COMMENT '状态 YesNoEnum。yes 生效；no 失效',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '摘要',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes 删除；no 未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_category
-- ----------------------------
INSERT INTO `kb_category` VALUES (1707244007706603521, 'SQL教程', 1707243486069403649, 1, 1, 'SQL表示结构化查询语言。SQL是一种标准编程语言，专门设计用于在关系数据库管理系统（RDBMS）内存储，检索，管理或处理数据。SQL于1987年成为ISO标准。', '2023-09-28 12:01:05', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707244074358288386, 'MySQL教程', 1707243486069403649, 1, 1, 'MySQL 是最流行的关系型数据库管理系统，在 WEB 应用方面 MySQL 是最好的 RDBMS(Relational Database Management System：关系数据库管理系统)应用软件之一。', '2023-09-28 12:01:21', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707244173268365314, 'SQLite 教程', 1707243486069403649, 1, 1, 'SQLite是一个软件库，可实现自包含，无服务器，零配置的事务型SQL数据库引擎。SQLite是世界上部署最广泛的SQL数据库引擎。SQLite的源代码在公共领域。本教程将为您提供SQLite的快速入门，并使您熟悉SQLite编程。', '2023-09-28 12:01:45', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707244234148687873, 'PostgreSQL 教程', 1707243486069403649, 1, 1, 'PostgreSQL 是一个免费的对象-关系数据库服务器(ORDBMS)，在灵活的BSD许可证下发行。\n\nPostgreSQL 开发者把它念作 post-gress-Q-L。\n\nPostgreSQL 的 Slogan 是 \"世界上最先进的开源关系型数据库\"。', '2023-09-28 12:01:59', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707244318017990658, 'MongoDB 教程', 1707243486069403649, 1, 1, 'MongoDB 是一个基于分布式文件存储的数据库。由 C++ 语言编写。旨在为 WEB 应用提供可扩展的高性能数据存储解决方案。MongoDB 是一个介于关系数据库和非关系数据库之间的产品，是非关系数据库当中功能最丰富，最像关系数据库的。', '2023-09-28 12:02:19', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707244366831300609, 'Redis 教程', 1707243486069403649, 1, 1, 'REmote DIctionary Server(Redis) 是一个由 Salvatore Sanfilippo 写的 key-value 存储系统，是跨平台的非关系型数据库。', '2023-09-28 12:02:31', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707244436918120449, 'Elasticsearch 教程', 1707243486069403649, 1, 1, 'Elasticsearch是一个基于Lucene的搜索服务器。它提供了一个分布式多用户能力的全文搜索引擎，基于RESTful web接口。Elasticsearch是用Java语言开发的，并作为Apache许可条款下的开放源码发布，是一种流行的企业级搜索引擎。', '2023-09-28 12:02:47', 1375360470249771009, 1375360470249771009, '2023-09-28 14:24:22', 2);
INSERT INTO `kb_category` VALUES (1707280012912386049, '深度学习', 1707272674356768770, 1, 1, '深度学习是基于现有的数据进行学习操作，是机器学习研究中的一个新的领域，它模仿人脑的机制来解释数据，例如图像，声音和文本，深度学习是无监督学习的一种。', '2023-09-28 14:24:09', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707280056801583105, '自然语言处理', 1707272674356768770, 1, 1, '自然语言处理是用自然语言同计算机进行通讯的一种技术。人工智能的分支学科，研究用电子计算机模拟人的语言交际过程，使计算机能理解和运用人类社会的自然语言如汉语、英语等，实现人机之间的自然语言通信，以代替人的部分脑力劳动。', '2023-09-28 14:24:20', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707280105220628482, '计算机视觉', 1707272674356768770, 1, 1, '计算机视觉是指用摄影机和电脑代替人眼对目标进行识别、跟踪和测量等机器视觉，并进一步做图形处理，使电脑处理成为更适合人眼观察或传送给仪器检测的图像。', '2023-09-28 14:24:31', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707280139727167490, '智能机器人', 1707272674356768770, 1, 1, '智能机器人的研发方向是，给机器人装上“大脑芯片”，从而使其智能性更强，在认知学 、自动组织、对模糊信息的综合处理等方面将会前进一大步。', '2023-09-28 14:24:40', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707280171905867777, '自动程序设计', 1707272674356768770, 1, 1, '自动程序设计是指根据给定问题的原始描述，自动生成满足要求的程序。它是软件工程和人工智能相结合的研究课题。', '2023-09-28 14:24:47', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707280206022336514, '数据挖掘', 1707272674356768770, 1, 1, '数据挖掘一般是指从大量的数据中通过算法搜索隐藏于其中信息的过程。它通常与计算机科学有关，并通过统计、在线分析处理、情报检索、机器学习、专家系统和模式识别等诸多方法来实现上述目标。', '2023-09-28 14:24:56', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_category` VALUES (1707304289250336769, 'Kubernetes', 1656835906222718972, 1, 1, 'Kubernetes 是一个全新的基于容器技术的分布式架构解决方案，是 Google 开源的一个容器集群管理系统，Kubernetes 简称 K8S。\n\nKubernetes 是一个一站式的完备的分布式系统开发和支撑平台，更是一个开放平台，对现有的编程语言、编程框架、中间件没有任何侵入性。', '2023-09-28 16:00:37', 1375360470249771009, NULL, NULL, 2);

-- ----------------------------
-- Table structure for kb_category_label
-- ----------------------------
DROP TABLE IF EXISTS `kb_category_label`;
CREATE TABLE `kb_category_label`  (
  `id` bigint NOT NULL COMMENT '主键Id',
  `ct_id` bigint NULL DEFAULT NULL COMMENT '标签分类Id',
  `l_id` bigint NULL DEFAULT NULL COMMENT '标签Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签分组和标签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_category_label
-- ----------------------------

-- ----------------------------
-- Table structure for kb_collect
-- ----------------------------
DROP TABLE IF EXISTS `kb_collect`;
CREATE TABLE `kb_collect`  (
  `id` bigint NOT NULL COMMENT '主键',
  `basic_id` bigint NULL DEFAULT NULL COMMENT '知识id',
  `status` int NULL DEFAULT NULL COMMENT '收藏和取消收藏',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建日期',
  `type` int NULL DEFAULT NULL COMMENT '1:普通知识库   2：视频知识库',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识收藏表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_collect
-- ----------------------------

-- ----------------------------
-- Table structure for kb_comment
-- ----------------------------
DROP TABLE IF EXISTS `kb_comment`;
CREATE TABLE `kb_comment`  (
  `id` bigint NOT NULL COMMENT '主键',
  `content` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '评价内容',
  `basic_id` bigint NULL DEFAULT NULL COMMENT '对应的知识id',
  `parent_id` bigint NULL DEFAULT NULL COMMENT '父id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  `one_id` bigint NULL DEFAULT NULL COMMENT '最上级id',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '1：删除  2：非删除',
  `is_reply` int NULL DEFAULT NULL COMMENT '1：有回复 2：无回复',
  `status` int NULL DEFAULT NULL COMMENT '1: 普通知识库 2：视频知识库',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识评论管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_comment
-- ----------------------------

-- ----------------------------
-- Table structure for kb_date
-- ----------------------------
DROP TABLE IF EXISTS `kb_date`;
CREATE TABLE `kb_date`  (
  `date_value` date NULL DEFAULT NULL
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_date
-- ----------------------------
INSERT INTO `kb_date` VALUES ('2024-01-01');
INSERT INTO `kb_date` VALUES ('2024-01-02');
INSERT INTO `kb_date` VALUES ('2024-01-03');
INSERT INTO `kb_date` VALUES ('2024-01-04');
INSERT INTO `kb_date` VALUES ('2024-01-05');
INSERT INTO `kb_date` VALUES ('2024-01-06');
INSERT INTO `kb_date` VALUES ('2024-01-07');
INSERT INTO `kb_date` VALUES ('2024-01-08');
INSERT INTO `kb_date` VALUES ('2024-01-09');
INSERT INTO `kb_date` VALUES ('2024-01-10');
INSERT INTO `kb_date` VALUES ('2024-01-11');
INSERT INTO `kb_date` VALUES ('2024-01-12');
INSERT INTO `kb_date` VALUES ('2024-01-13');
INSERT INTO `kb_date` VALUES ('2024-01-14');
INSERT INTO `kb_date` VALUES ('2024-01-15');
INSERT INTO `kb_date` VALUES ('2024-01-16');
INSERT INTO `kb_date` VALUES ('2024-01-17');
INSERT INTO `kb_date` VALUES ('2024-01-18');
INSERT INTO `kb_date` VALUES ('2024-01-19');
INSERT INTO `kb_date` VALUES ('2024-01-20');
INSERT INTO `kb_date` VALUES ('2024-01-21');
INSERT INTO `kb_date` VALUES ('2024-01-22');
INSERT INTO `kb_date` VALUES ('2024-01-23');
INSERT INTO `kb_date` VALUES ('2024-01-24');
INSERT INTO `kb_date` VALUES ('2024-01-25');
INSERT INTO `kb_date` VALUES ('2024-01-26');
INSERT INTO `kb_date` VALUES ('2024-01-27');
INSERT INTO `kb_date` VALUES ('2024-01-28');
INSERT INTO `kb_date` VALUES ('2024-01-29');
INSERT INTO `kb_date` VALUES ('2024-01-30');
INSERT INTO `kb_date` VALUES ('2024-01-31');
INSERT INTO `kb_date` VALUES ('2024-02-01');
INSERT INTO `kb_date` VALUES ('2024-02-02');
INSERT INTO `kb_date` VALUES ('2024-02-03');
INSERT INTO `kb_date` VALUES ('2024-02-04');
INSERT INTO `kb_date` VALUES ('2024-02-05');
INSERT INTO `kb_date` VALUES ('2024-02-06');
INSERT INTO `kb_date` VALUES ('2024-02-07');
INSERT INTO `kb_date` VALUES ('2024-02-08');
INSERT INTO `kb_date` VALUES ('2024-02-09');
INSERT INTO `kb_date` VALUES ('2024-02-10');
INSERT INTO `kb_date` VALUES ('2024-02-11');
INSERT INTO `kb_date` VALUES ('2024-02-12');
INSERT INTO `kb_date` VALUES ('2024-02-13');
INSERT INTO `kb_date` VALUES ('2024-02-14');
INSERT INTO `kb_date` VALUES ('2024-02-15');
INSERT INTO `kb_date` VALUES ('2024-02-16');
INSERT INTO `kb_date` VALUES ('2024-02-17');
INSERT INTO `kb_date` VALUES ('2024-02-18');
INSERT INTO `kb_date` VALUES ('2024-02-19');
INSERT INTO `kb_date` VALUES ('2024-02-20');
INSERT INTO `kb_date` VALUES ('2024-02-21');
INSERT INTO `kb_date` VALUES ('2024-02-22');
INSERT INTO `kb_date` VALUES ('2024-02-23');
INSERT INTO `kb_date` VALUES ('2024-02-24');
INSERT INTO `kb_date` VALUES ('2024-02-25');
INSERT INTO `kb_date` VALUES ('2024-02-26');
INSERT INTO `kb_date` VALUES ('2024-02-27');
INSERT INTO `kb_date` VALUES ('2024-02-28');
INSERT INTO `kb_date` VALUES ('2024-02-29');
INSERT INTO `kb_date` VALUES ('2024-03-01');
INSERT INTO `kb_date` VALUES ('2024-03-02');
INSERT INTO `kb_date` VALUES ('2024-03-03');
INSERT INTO `kb_date` VALUES ('2024-03-04');
INSERT INTO `kb_date` VALUES ('2024-03-05');
INSERT INTO `kb_date` VALUES ('2024-03-06');
INSERT INTO `kb_date` VALUES ('2024-03-07');
INSERT INTO `kb_date` VALUES ('2024-03-08');
INSERT INTO `kb_date` VALUES ('2024-03-09');
INSERT INTO `kb_date` VALUES ('2024-03-10');
INSERT INTO `kb_date` VALUES ('2024-03-11');
INSERT INTO `kb_date` VALUES ('2024-03-12');
INSERT INTO `kb_date` VALUES ('2024-03-13');
INSERT INTO `kb_date` VALUES ('2024-03-14');
INSERT INTO `kb_date` VALUES ('2024-03-15');
INSERT INTO `kb_date` VALUES ('2024-03-16');
INSERT INTO `kb_date` VALUES ('2024-03-17');
INSERT INTO `kb_date` VALUES ('2024-03-18');
INSERT INTO `kb_date` VALUES ('2024-03-19');
INSERT INTO `kb_date` VALUES ('2024-03-20');
INSERT INTO `kb_date` VALUES ('2024-03-21');
INSERT INTO `kb_date` VALUES ('2024-03-22');
INSERT INTO `kb_date` VALUES ('2024-03-23');
INSERT INTO `kb_date` VALUES ('2024-03-24');
INSERT INTO `kb_date` VALUES ('2024-03-25');
INSERT INTO `kb_date` VALUES ('2024-03-26');
INSERT INTO `kb_date` VALUES ('2024-03-27');
INSERT INTO `kb_date` VALUES ('2024-03-28');
INSERT INTO `kb_date` VALUES ('2024-03-29');
INSERT INTO `kb_date` VALUES ('2024-03-30');
INSERT INTO `kb_date` VALUES ('2024-03-31');
INSERT INTO `kb_date` VALUES ('2024-04-01');
INSERT INTO `kb_date` VALUES ('2024-04-02');
INSERT INTO `kb_date` VALUES ('2024-04-03');
INSERT INTO `kb_date` VALUES ('2024-04-04');
INSERT INTO `kb_date` VALUES ('2024-04-05');
INSERT INTO `kb_date` VALUES ('2024-04-06');
INSERT INTO `kb_date` VALUES ('2024-04-07');
INSERT INTO `kb_date` VALUES ('2024-04-08');
INSERT INTO `kb_date` VALUES ('2024-04-09');
INSERT INTO `kb_date` VALUES ('2024-04-10');
INSERT INTO `kb_date` VALUES ('2024-04-11');
INSERT INTO `kb_date` VALUES ('2024-04-12');
INSERT INTO `kb_date` VALUES ('2024-04-13');
INSERT INTO `kb_date` VALUES ('2024-04-14');
INSERT INTO `kb_date` VALUES ('2024-04-15');
INSERT INTO `kb_date` VALUES ('2024-04-16');
INSERT INTO `kb_date` VALUES ('2024-04-17');
INSERT INTO `kb_date` VALUES ('2024-04-18');
INSERT INTO `kb_date` VALUES ('2024-04-19');
INSERT INTO `kb_date` VALUES ('2024-04-20');
INSERT INTO `kb_date` VALUES ('2024-04-21');
INSERT INTO `kb_date` VALUES ('2024-04-22');
INSERT INTO `kb_date` VALUES ('2024-04-23');
INSERT INTO `kb_date` VALUES ('2024-04-24');
INSERT INTO `kb_date` VALUES ('2024-04-25');
INSERT INTO `kb_date` VALUES ('2024-04-26');
INSERT INTO `kb_date` VALUES ('2024-04-27');
INSERT INTO `kb_date` VALUES ('2024-04-28');
INSERT INTO `kb_date` VALUES ('2024-04-29');
INSERT INTO `kb_date` VALUES ('2024-04-30');
INSERT INTO `kb_date` VALUES ('2024-05-01');
INSERT INTO `kb_date` VALUES ('2024-05-02');
INSERT INTO `kb_date` VALUES ('2024-05-03');
INSERT INTO `kb_date` VALUES ('2024-05-04');
INSERT INTO `kb_date` VALUES ('2024-05-05');
INSERT INTO `kb_date` VALUES ('2024-05-06');
INSERT INTO `kb_date` VALUES ('2024-05-07');
INSERT INTO `kb_date` VALUES ('2024-05-08');
INSERT INTO `kb_date` VALUES ('2024-05-09');
INSERT INTO `kb_date` VALUES ('2024-05-10');
INSERT INTO `kb_date` VALUES ('2024-05-11');
INSERT INTO `kb_date` VALUES ('2024-05-12');
INSERT INTO `kb_date` VALUES ('2024-05-13');
INSERT INTO `kb_date` VALUES ('2024-05-14');
INSERT INTO `kb_date` VALUES ('2024-05-15');
INSERT INTO `kb_date` VALUES ('2024-05-16');
INSERT INTO `kb_date` VALUES ('2024-05-17');
INSERT INTO `kb_date` VALUES ('2024-05-18');
INSERT INTO `kb_date` VALUES ('2024-05-19');
INSERT INTO `kb_date` VALUES ('2024-05-20');
INSERT INTO `kb_date` VALUES ('2024-05-21');
INSERT INTO `kb_date` VALUES ('2024-05-22');
INSERT INTO `kb_date` VALUES ('2024-05-23');
INSERT INTO `kb_date` VALUES ('2024-05-24');
INSERT INTO `kb_date` VALUES ('2024-05-25');
INSERT INTO `kb_date` VALUES ('2024-05-26');
INSERT INTO `kb_date` VALUES ('2024-05-27');
INSERT INTO `kb_date` VALUES ('2024-05-28');
INSERT INTO `kb_date` VALUES ('2024-05-29');
INSERT INTO `kb_date` VALUES ('2024-05-30');
INSERT INTO `kb_date` VALUES ('2024-05-31');
INSERT INTO `kb_date` VALUES ('2024-06-01');
INSERT INTO `kb_date` VALUES ('2024-06-02');
INSERT INTO `kb_date` VALUES ('2024-06-03');
INSERT INTO `kb_date` VALUES ('2024-06-04');
INSERT INTO `kb_date` VALUES ('2024-06-05');
INSERT INTO `kb_date` VALUES ('2024-06-06');
INSERT INTO `kb_date` VALUES ('2024-06-07');
INSERT INTO `kb_date` VALUES ('2024-06-08');
INSERT INTO `kb_date` VALUES ('2024-06-09');
INSERT INTO `kb_date` VALUES ('2024-06-10');
INSERT INTO `kb_date` VALUES ('2024-06-11');
INSERT INTO `kb_date` VALUES ('2024-06-12');
INSERT INTO `kb_date` VALUES ('2024-06-13');
INSERT INTO `kb_date` VALUES ('2024-06-14');
INSERT INTO `kb_date` VALUES ('2024-06-15');
INSERT INTO `kb_date` VALUES ('2024-06-16');
INSERT INTO `kb_date` VALUES ('2024-06-17');
INSERT INTO `kb_date` VALUES ('2024-06-18');
INSERT INTO `kb_date` VALUES ('2024-06-19');
INSERT INTO `kb_date` VALUES ('2024-06-20');
INSERT INTO `kb_date` VALUES ('2024-06-21');
INSERT INTO `kb_date` VALUES ('2024-06-22');
INSERT INTO `kb_date` VALUES ('2024-06-23');
INSERT INTO `kb_date` VALUES ('2024-06-24');
INSERT INTO `kb_date` VALUES ('2024-06-25');
INSERT INTO `kb_date` VALUES ('2024-06-26');
INSERT INTO `kb_date` VALUES ('2024-06-27');
INSERT INTO `kb_date` VALUES ('2024-06-28');
INSERT INTO `kb_date` VALUES ('2024-06-29');
INSERT INTO `kb_date` VALUES ('2024-06-30');
INSERT INTO `kb_date` VALUES ('2024-07-01');
INSERT INTO `kb_date` VALUES ('2024-07-02');
INSERT INTO `kb_date` VALUES ('2024-07-03');
INSERT INTO `kb_date` VALUES ('2024-07-04');
INSERT INTO `kb_date` VALUES ('2024-07-05');
INSERT INTO `kb_date` VALUES ('2024-07-06');
INSERT INTO `kb_date` VALUES ('2024-07-07');
INSERT INTO `kb_date` VALUES ('2024-07-08');
INSERT INTO `kb_date` VALUES ('2024-07-09');
INSERT INTO `kb_date` VALUES ('2024-07-10');
INSERT INTO `kb_date` VALUES ('2024-07-11');
INSERT INTO `kb_date` VALUES ('2024-07-12');
INSERT INTO `kb_date` VALUES ('2024-07-13');
INSERT INTO `kb_date` VALUES ('2024-07-14');
INSERT INTO `kb_date` VALUES ('2024-07-15');
INSERT INTO `kb_date` VALUES ('2024-07-16');
INSERT INTO `kb_date` VALUES ('2024-07-17');
INSERT INTO `kb_date` VALUES ('2024-07-18');
INSERT INTO `kb_date` VALUES ('2024-07-19');
INSERT INTO `kb_date` VALUES ('2024-07-20');
INSERT INTO `kb_date` VALUES ('2024-07-21');
INSERT INTO `kb_date` VALUES ('2024-07-22');
INSERT INTO `kb_date` VALUES ('2024-07-23');
INSERT INTO `kb_date` VALUES ('2024-07-24');
INSERT INTO `kb_date` VALUES ('2024-07-25');
INSERT INTO `kb_date` VALUES ('2024-07-26');
INSERT INTO `kb_date` VALUES ('2024-07-27');
INSERT INTO `kb_date` VALUES ('2024-07-28');
INSERT INTO `kb_date` VALUES ('2024-07-29');
INSERT INTO `kb_date` VALUES ('2024-07-30');
INSERT INTO `kb_date` VALUES ('2024-07-31');
INSERT INTO `kb_date` VALUES ('2024-08-01');
INSERT INTO `kb_date` VALUES ('2024-08-02');
INSERT INTO `kb_date` VALUES ('2024-08-03');
INSERT INTO `kb_date` VALUES ('2024-08-04');
INSERT INTO `kb_date` VALUES ('2024-08-05');
INSERT INTO `kb_date` VALUES ('2024-08-06');
INSERT INTO `kb_date` VALUES ('2024-08-07');
INSERT INTO `kb_date` VALUES ('2024-08-08');
INSERT INTO `kb_date` VALUES ('2024-08-09');
INSERT INTO `kb_date` VALUES ('2024-08-10');
INSERT INTO `kb_date` VALUES ('2024-08-11');
INSERT INTO `kb_date` VALUES ('2024-08-12');
INSERT INTO `kb_date` VALUES ('2024-08-13');
INSERT INTO `kb_date` VALUES ('2024-08-14');
INSERT INTO `kb_date` VALUES ('2024-08-15');
INSERT INTO `kb_date` VALUES ('2024-08-16');
INSERT INTO `kb_date` VALUES ('2024-08-17');
INSERT INTO `kb_date` VALUES ('2024-08-18');
INSERT INTO `kb_date` VALUES ('2024-08-19');
INSERT INTO `kb_date` VALUES ('2024-08-20');
INSERT INTO `kb_date` VALUES ('2024-08-21');
INSERT INTO `kb_date` VALUES ('2024-08-22');
INSERT INTO `kb_date` VALUES ('2024-08-23');
INSERT INTO `kb_date` VALUES ('2024-08-24');
INSERT INTO `kb_date` VALUES ('2024-08-25');
INSERT INTO `kb_date` VALUES ('2024-08-26');
INSERT INTO `kb_date` VALUES ('2024-08-27');
INSERT INTO `kb_date` VALUES ('2024-08-28');
INSERT INTO `kb_date` VALUES ('2024-08-29');
INSERT INTO `kb_date` VALUES ('2024-08-30');
INSERT INTO `kb_date` VALUES ('2024-08-31');
INSERT INTO `kb_date` VALUES ('2024-09-01');
INSERT INTO `kb_date` VALUES ('2024-09-02');
INSERT INTO `kb_date` VALUES ('2024-09-03');
INSERT INTO `kb_date` VALUES ('2024-09-04');
INSERT INTO `kb_date` VALUES ('2024-09-05');
INSERT INTO `kb_date` VALUES ('2024-09-06');
INSERT INTO `kb_date` VALUES ('2024-09-07');
INSERT INTO `kb_date` VALUES ('2024-09-08');
INSERT INTO `kb_date` VALUES ('2024-09-09');
INSERT INTO `kb_date` VALUES ('2024-09-10');
INSERT INTO `kb_date` VALUES ('2024-09-11');
INSERT INTO `kb_date` VALUES ('2024-09-12');
INSERT INTO `kb_date` VALUES ('2024-09-13');
INSERT INTO `kb_date` VALUES ('2024-09-14');
INSERT INTO `kb_date` VALUES ('2024-09-15');
INSERT INTO `kb_date` VALUES ('2024-09-16');
INSERT INTO `kb_date` VALUES ('2024-09-17');
INSERT INTO `kb_date` VALUES ('2024-09-18');
INSERT INTO `kb_date` VALUES ('2024-09-19');
INSERT INTO `kb_date` VALUES ('2024-09-20');
INSERT INTO `kb_date` VALUES ('2024-09-21');
INSERT INTO `kb_date` VALUES ('2024-09-22');
INSERT INTO `kb_date` VALUES ('2024-09-23');
INSERT INTO `kb_date` VALUES ('2024-09-24');
INSERT INTO `kb_date` VALUES ('2024-09-25');
INSERT INTO `kb_date` VALUES ('2024-09-26');
INSERT INTO `kb_date` VALUES ('2024-09-27');
INSERT INTO `kb_date` VALUES ('2024-09-28');
INSERT INTO `kb_date` VALUES ('2024-09-29');
INSERT INTO `kb_date` VALUES ('2024-09-30');
INSERT INTO `kb_date` VALUES ('2024-10-01');
INSERT INTO `kb_date` VALUES ('2024-10-02');
INSERT INTO `kb_date` VALUES ('2024-10-03');
INSERT INTO `kb_date` VALUES ('2024-10-04');
INSERT INTO `kb_date` VALUES ('2024-10-05');
INSERT INTO `kb_date` VALUES ('2024-10-06');
INSERT INTO `kb_date` VALUES ('2024-10-07');
INSERT INTO `kb_date` VALUES ('2024-10-08');
INSERT INTO `kb_date` VALUES ('2024-10-09');
INSERT INTO `kb_date` VALUES ('2024-10-10');
INSERT INTO `kb_date` VALUES ('2024-10-11');
INSERT INTO `kb_date` VALUES ('2024-10-12');
INSERT INTO `kb_date` VALUES ('2024-10-13');
INSERT INTO `kb_date` VALUES ('2024-10-14');
INSERT INTO `kb_date` VALUES ('2024-10-15');
INSERT INTO `kb_date` VALUES ('2024-10-16');
INSERT INTO `kb_date` VALUES ('2024-10-17');
INSERT INTO `kb_date` VALUES ('2024-10-18');
INSERT INTO `kb_date` VALUES ('2024-10-19');
INSERT INTO `kb_date` VALUES ('2024-10-20');
INSERT INTO `kb_date` VALUES ('2024-10-21');
INSERT INTO `kb_date` VALUES ('2024-10-22');
INSERT INTO `kb_date` VALUES ('2024-10-23');
INSERT INTO `kb_date` VALUES ('2024-10-24');
INSERT INTO `kb_date` VALUES ('2024-10-25');
INSERT INTO `kb_date` VALUES ('2024-10-26');
INSERT INTO `kb_date` VALUES ('2024-10-27');
INSERT INTO `kb_date` VALUES ('2024-10-28');
INSERT INTO `kb_date` VALUES ('2024-10-29');
INSERT INTO `kb_date` VALUES ('2024-10-30');
INSERT INTO `kb_date` VALUES ('2024-10-31');
INSERT INTO `kb_date` VALUES ('2024-11-01');
INSERT INTO `kb_date` VALUES ('2024-11-02');
INSERT INTO `kb_date` VALUES ('2024-11-03');
INSERT INTO `kb_date` VALUES ('2024-11-04');
INSERT INTO `kb_date` VALUES ('2024-11-05');
INSERT INTO `kb_date` VALUES ('2024-11-06');
INSERT INTO `kb_date` VALUES ('2024-11-07');
INSERT INTO `kb_date` VALUES ('2024-11-08');
INSERT INTO `kb_date` VALUES ('2024-11-09');
INSERT INTO `kb_date` VALUES ('2024-11-10');
INSERT INTO `kb_date` VALUES ('2024-11-11');
INSERT INTO `kb_date` VALUES ('2024-11-12');
INSERT INTO `kb_date` VALUES ('2024-11-13');
INSERT INTO `kb_date` VALUES ('2024-11-14');
INSERT INTO `kb_date` VALUES ('2024-11-15');
INSERT INTO `kb_date` VALUES ('2024-11-16');
INSERT INTO `kb_date` VALUES ('2024-11-17');
INSERT INTO `kb_date` VALUES ('2024-11-18');
INSERT INTO `kb_date` VALUES ('2024-11-19');
INSERT INTO `kb_date` VALUES ('2024-11-20');
INSERT INTO `kb_date` VALUES ('2024-11-21');
INSERT INTO `kb_date` VALUES ('2024-11-22');
INSERT INTO `kb_date` VALUES ('2024-11-23');
INSERT INTO `kb_date` VALUES ('2024-11-24');
INSERT INTO `kb_date` VALUES ('2024-11-25');
INSERT INTO `kb_date` VALUES ('2024-11-26');
INSERT INTO `kb_date` VALUES ('2024-11-27');
INSERT INTO `kb_date` VALUES ('2024-11-28');
INSERT INTO `kb_date` VALUES ('2024-11-29');
INSERT INTO `kb_date` VALUES ('2024-11-30');
INSERT INTO `kb_date` VALUES ('2024-12-01');
INSERT INTO `kb_date` VALUES ('2024-12-02');
INSERT INTO `kb_date` VALUES ('2024-12-03');
INSERT INTO `kb_date` VALUES ('2024-12-04');
INSERT INTO `kb_date` VALUES ('2024-12-05');
INSERT INTO `kb_date` VALUES ('2024-12-06');
INSERT INTO `kb_date` VALUES ('2024-12-07');
INSERT INTO `kb_date` VALUES ('2024-12-08');
INSERT INTO `kb_date` VALUES ('2024-12-09');
INSERT INTO `kb_date` VALUES ('2024-12-10');
INSERT INTO `kb_date` VALUES ('2024-12-11');
INSERT INTO `kb_date` VALUES ('2024-12-12');
INSERT INTO `kb_date` VALUES ('2024-12-13');
INSERT INTO `kb_date` VALUES ('2024-12-14');
INSERT INTO `kb_date` VALUES ('2024-12-15');
INSERT INTO `kb_date` VALUES ('2024-12-16');
INSERT INTO `kb_date` VALUES ('2024-12-17');
INSERT INTO `kb_date` VALUES ('2024-12-18');
INSERT INTO `kb_date` VALUES ('2024-12-19');
INSERT INTO `kb_date` VALUES ('2024-12-20');
INSERT INTO `kb_date` VALUES ('2024-12-21');
INSERT INTO `kb_date` VALUES ('2024-12-22');
INSERT INTO `kb_date` VALUES ('2024-12-23');
INSERT INTO `kb_date` VALUES ('2024-12-24');
INSERT INTO `kb_date` VALUES ('2024-12-25');
INSERT INTO `kb_date` VALUES ('2024-12-26');
INSERT INTO `kb_date` VALUES ('2024-12-27');
INSERT INTO `kb_date` VALUES ('2024-12-28');
INSERT INTO `kb_date` VALUES ('2024-12-29');
INSERT INTO `kb_date` VALUES ('2024-12-30');
INSERT INTO `kb_date` VALUES ('2024-12-31');
INSERT INTO `kb_date` VALUES ('2025-01-01');
INSERT INTO `kb_date` VALUES ('2025-01-02');
INSERT INTO `kb_date` VALUES ('2025-01-03');
INSERT INTO `kb_date` VALUES ('2025-01-04');
INSERT INTO `kb_date` VALUES ('2025-01-05');
INSERT INTO `kb_date` VALUES ('2025-01-06');
INSERT INTO `kb_date` VALUES ('2025-01-07');
INSERT INTO `kb_date` VALUES ('2025-01-08');
INSERT INTO `kb_date` VALUES ('2025-01-09');
INSERT INTO `kb_date` VALUES ('2025-01-10');
INSERT INTO `kb_date` VALUES ('2025-01-11');
INSERT INTO `kb_date` VALUES ('2025-01-12');
INSERT INTO `kb_date` VALUES ('2025-01-13');
INSERT INTO `kb_date` VALUES ('2025-01-14');
INSERT INTO `kb_date` VALUES ('2025-01-15');
INSERT INTO `kb_date` VALUES ('2025-01-16');
INSERT INTO `kb_date` VALUES ('2025-01-17');
INSERT INTO `kb_date` VALUES ('2025-01-18');
INSERT INTO `kb_date` VALUES ('2025-01-19');
INSERT INTO `kb_date` VALUES ('2025-01-20');
INSERT INTO `kb_date` VALUES ('2025-01-21');
INSERT INTO `kb_date` VALUES ('2025-01-22');
INSERT INTO `kb_date` VALUES ('2025-01-23');
INSERT INTO `kb_date` VALUES ('2025-01-24');
INSERT INTO `kb_date` VALUES ('2025-01-25');
INSERT INTO `kb_date` VALUES ('2025-01-26');
INSERT INTO `kb_date` VALUES ('2025-01-27');
INSERT INTO `kb_date` VALUES ('2025-01-28');
INSERT INTO `kb_date` VALUES ('2025-01-29');
INSERT INTO `kb_date` VALUES ('2025-01-30');
INSERT INTO `kb_date` VALUES ('2025-01-31');
INSERT INTO `kb_date` VALUES ('2025-02-01');
INSERT INTO `kb_date` VALUES ('2025-02-02');
INSERT INTO `kb_date` VALUES ('2025-02-03');
INSERT INTO `kb_date` VALUES ('2025-02-04');
INSERT INTO `kb_date` VALUES ('2025-02-05');
INSERT INTO `kb_date` VALUES ('2025-02-06');
INSERT INTO `kb_date` VALUES ('2025-02-07');
INSERT INTO `kb_date` VALUES ('2025-02-08');
INSERT INTO `kb_date` VALUES ('2025-02-09');
INSERT INTO `kb_date` VALUES ('2025-02-10');
INSERT INTO `kb_date` VALUES ('2025-02-11');
INSERT INTO `kb_date` VALUES ('2025-02-12');
INSERT INTO `kb_date` VALUES ('2025-02-13');
INSERT INTO `kb_date` VALUES ('2025-02-14');
INSERT INTO `kb_date` VALUES ('2025-02-15');
INSERT INTO `kb_date` VALUES ('2025-02-16');
INSERT INTO `kb_date` VALUES ('2025-02-17');
INSERT INTO `kb_date` VALUES ('2025-02-18');
INSERT INTO `kb_date` VALUES ('2025-02-19');
INSERT INTO `kb_date` VALUES ('2025-02-20');
INSERT INTO `kb_date` VALUES ('2025-02-21');
INSERT INTO `kb_date` VALUES ('2025-02-22');
INSERT INTO `kb_date` VALUES ('2025-02-23');
INSERT INTO `kb_date` VALUES ('2025-02-24');
INSERT INTO `kb_date` VALUES ('2025-02-25');
INSERT INTO `kb_date` VALUES ('2025-02-26');
INSERT INTO `kb_date` VALUES ('2025-02-27');
INSERT INTO `kb_date` VALUES ('2025-02-28');
INSERT INTO `kb_date` VALUES ('2025-03-01');
INSERT INTO `kb_date` VALUES ('2025-03-02');
INSERT INTO `kb_date` VALUES ('2025-03-03');
INSERT INTO `kb_date` VALUES ('2025-03-04');
INSERT INTO `kb_date` VALUES ('2025-03-05');
INSERT INTO `kb_date` VALUES ('2025-03-06');
INSERT INTO `kb_date` VALUES ('2025-03-07');
INSERT INTO `kb_date` VALUES ('2025-03-08');
INSERT INTO `kb_date` VALUES ('2025-03-09');
INSERT INTO `kb_date` VALUES ('2025-03-10');
INSERT INTO `kb_date` VALUES ('2025-03-11');
INSERT INTO `kb_date` VALUES ('2025-03-12');
INSERT INTO `kb_date` VALUES ('2025-03-13');
INSERT INTO `kb_date` VALUES ('2025-03-14');
INSERT INTO `kb_date` VALUES ('2025-03-15');
INSERT INTO `kb_date` VALUES ('2025-03-16');
INSERT INTO `kb_date` VALUES ('2025-03-17');
INSERT INTO `kb_date` VALUES ('2025-03-18');
INSERT INTO `kb_date` VALUES ('2025-03-19');
INSERT INTO `kb_date` VALUES ('2025-03-20');
INSERT INTO `kb_date` VALUES ('2025-03-21');
INSERT INTO `kb_date` VALUES ('2025-03-22');
INSERT INTO `kb_date` VALUES ('2025-03-23');
INSERT INTO `kb_date` VALUES ('2025-03-24');
INSERT INTO `kb_date` VALUES ('2025-03-25');
INSERT INTO `kb_date` VALUES ('2025-03-26');
INSERT INTO `kb_date` VALUES ('2025-03-27');
INSERT INTO `kb_date` VALUES ('2025-03-28');
INSERT INTO `kb_date` VALUES ('2025-03-29');
INSERT INTO `kb_date` VALUES ('2025-03-30');
INSERT INTO `kb_date` VALUES ('2025-03-31');
INSERT INTO `kb_date` VALUES ('2025-04-01');
INSERT INTO `kb_date` VALUES ('2025-04-02');
INSERT INTO `kb_date` VALUES ('2025-04-03');
INSERT INTO `kb_date` VALUES ('2025-04-04');
INSERT INTO `kb_date` VALUES ('2025-04-05');
INSERT INTO `kb_date` VALUES ('2025-04-06');
INSERT INTO `kb_date` VALUES ('2025-04-07');
INSERT INTO `kb_date` VALUES ('2025-04-08');
INSERT INTO `kb_date` VALUES ('2025-04-09');
INSERT INTO `kb_date` VALUES ('2025-04-10');
INSERT INTO `kb_date` VALUES ('2025-04-11');
INSERT INTO `kb_date` VALUES ('2025-04-12');
INSERT INTO `kb_date` VALUES ('2025-04-13');
INSERT INTO `kb_date` VALUES ('2025-04-14');
INSERT INTO `kb_date` VALUES ('2025-04-15');
INSERT INTO `kb_date` VALUES ('2025-04-16');
INSERT INTO `kb_date` VALUES ('2025-04-17');
INSERT INTO `kb_date` VALUES ('2025-04-18');
INSERT INTO `kb_date` VALUES ('2025-04-19');
INSERT INTO `kb_date` VALUES ('2025-04-20');
INSERT INTO `kb_date` VALUES ('2025-04-21');
INSERT INTO `kb_date` VALUES ('2025-04-22');
INSERT INTO `kb_date` VALUES ('2025-04-23');
INSERT INTO `kb_date` VALUES ('2025-04-24');
INSERT INTO `kb_date` VALUES ('2025-04-25');
INSERT INTO `kb_date` VALUES ('2025-04-26');
INSERT INTO `kb_date` VALUES ('2025-04-27');
INSERT INTO `kb_date` VALUES ('2025-04-28');
INSERT INTO `kb_date` VALUES ('2025-04-29');
INSERT INTO `kb_date` VALUES ('2025-04-30');
INSERT INTO `kb_date` VALUES ('2025-05-01');
INSERT INTO `kb_date` VALUES ('2025-05-02');
INSERT INTO `kb_date` VALUES ('2025-05-03');
INSERT INTO `kb_date` VALUES ('2025-05-04');
INSERT INTO `kb_date` VALUES ('2025-05-05');
INSERT INTO `kb_date` VALUES ('2025-05-06');
INSERT INTO `kb_date` VALUES ('2025-05-07');
INSERT INTO `kb_date` VALUES ('2025-05-08');
INSERT INTO `kb_date` VALUES ('2025-05-09');
INSERT INTO `kb_date` VALUES ('2025-05-10');
INSERT INTO `kb_date` VALUES ('2025-05-11');
INSERT INTO `kb_date` VALUES ('2025-05-12');
INSERT INTO `kb_date` VALUES ('2025-05-13');
INSERT INTO `kb_date` VALUES ('2025-05-14');
INSERT INTO `kb_date` VALUES ('2025-05-15');
INSERT INTO `kb_date` VALUES ('2025-05-16');
INSERT INTO `kb_date` VALUES ('2025-05-17');
INSERT INTO `kb_date` VALUES ('2025-05-18');
INSERT INTO `kb_date` VALUES ('2025-05-19');
INSERT INTO `kb_date` VALUES ('2025-05-20');
INSERT INTO `kb_date` VALUES ('2025-05-21');
INSERT INTO `kb_date` VALUES ('2025-05-22');
INSERT INTO `kb_date` VALUES ('2025-05-23');
INSERT INTO `kb_date` VALUES ('2025-05-24');
INSERT INTO `kb_date` VALUES ('2025-05-25');
INSERT INTO `kb_date` VALUES ('2025-05-26');
INSERT INTO `kb_date` VALUES ('2025-05-27');
INSERT INTO `kb_date` VALUES ('2025-05-28');
INSERT INTO `kb_date` VALUES ('2025-05-29');
INSERT INTO `kb_date` VALUES ('2025-05-30');
INSERT INTO `kb_date` VALUES ('2025-05-31');
INSERT INTO `kb_date` VALUES ('2025-06-01');
INSERT INTO `kb_date` VALUES ('2025-06-02');
INSERT INTO `kb_date` VALUES ('2025-06-03');
INSERT INTO `kb_date` VALUES ('2025-06-04');
INSERT INTO `kb_date` VALUES ('2025-06-05');
INSERT INTO `kb_date` VALUES ('2025-06-06');
INSERT INTO `kb_date` VALUES ('2025-06-07');
INSERT INTO `kb_date` VALUES ('2025-06-08');
INSERT INTO `kb_date` VALUES ('2025-06-09');
INSERT INTO `kb_date` VALUES ('2025-06-10');
INSERT INTO `kb_date` VALUES ('2025-06-11');
INSERT INTO `kb_date` VALUES ('2025-06-12');
INSERT INTO `kb_date` VALUES ('2025-06-13');
INSERT INTO `kb_date` VALUES ('2025-06-14');
INSERT INTO `kb_date` VALUES ('2025-06-15');
INSERT INTO `kb_date` VALUES ('2025-06-16');
INSERT INTO `kb_date` VALUES ('2025-06-17');
INSERT INTO `kb_date` VALUES ('2025-06-18');
INSERT INTO `kb_date` VALUES ('2025-06-19');
INSERT INTO `kb_date` VALUES ('2025-06-20');
INSERT INTO `kb_date` VALUES ('2025-06-21');
INSERT INTO `kb_date` VALUES ('2025-06-22');
INSERT INTO `kb_date` VALUES ('2025-06-23');
INSERT INTO `kb_date` VALUES ('2025-06-24');
INSERT INTO `kb_date` VALUES ('2025-06-25');
INSERT INTO `kb_date` VALUES ('2025-06-26');
INSERT INTO `kb_date` VALUES ('2025-06-27');
INSERT INTO `kb_date` VALUES ('2025-06-28');
INSERT INTO `kb_date` VALUES ('2025-06-29');
INSERT INTO `kb_date` VALUES ('2025-06-30');
INSERT INTO `kb_date` VALUES ('2025-07-01');
INSERT INTO `kb_date` VALUES ('2025-07-02');
INSERT INTO `kb_date` VALUES ('2025-07-03');
INSERT INTO `kb_date` VALUES ('2025-07-04');
INSERT INTO `kb_date` VALUES ('2025-07-05');
INSERT INTO `kb_date` VALUES ('2025-07-06');
INSERT INTO `kb_date` VALUES ('2025-07-07');
INSERT INTO `kb_date` VALUES ('2025-07-08');
INSERT INTO `kb_date` VALUES ('2025-07-09');
INSERT INTO `kb_date` VALUES ('2025-07-10');
INSERT INTO `kb_date` VALUES ('2025-07-11');
INSERT INTO `kb_date` VALUES ('2025-07-12');
INSERT INTO `kb_date` VALUES ('2025-07-13');
INSERT INTO `kb_date` VALUES ('2025-07-14');
INSERT INTO `kb_date` VALUES ('2025-07-15');
INSERT INTO `kb_date` VALUES ('2025-07-16');
INSERT INTO `kb_date` VALUES ('2025-07-17');
INSERT INTO `kb_date` VALUES ('2025-07-18');
INSERT INTO `kb_date` VALUES ('2025-07-19');
INSERT INTO `kb_date` VALUES ('2025-07-20');
INSERT INTO `kb_date` VALUES ('2025-07-21');
INSERT INTO `kb_date` VALUES ('2025-07-22');
INSERT INTO `kb_date` VALUES ('2025-07-23');
INSERT INTO `kb_date` VALUES ('2025-07-24');
INSERT INTO `kb_date` VALUES ('2025-07-25');
INSERT INTO `kb_date` VALUES ('2025-07-26');
INSERT INTO `kb_date` VALUES ('2025-07-27');
INSERT INTO `kb_date` VALUES ('2025-07-28');
INSERT INTO `kb_date` VALUES ('2025-07-29');
INSERT INTO `kb_date` VALUES ('2025-07-30');
INSERT INTO `kb_date` VALUES ('2025-07-31');
INSERT INTO `kb_date` VALUES ('2025-08-01');
INSERT INTO `kb_date` VALUES ('2025-08-02');
INSERT INTO `kb_date` VALUES ('2025-08-03');
INSERT INTO `kb_date` VALUES ('2025-08-04');
INSERT INTO `kb_date` VALUES ('2025-08-05');
INSERT INTO `kb_date` VALUES ('2025-08-06');
INSERT INTO `kb_date` VALUES ('2025-08-07');
INSERT INTO `kb_date` VALUES ('2025-08-08');
INSERT INTO `kb_date` VALUES ('2025-08-09');
INSERT INTO `kb_date` VALUES ('2025-08-10');
INSERT INTO `kb_date` VALUES ('2025-08-11');
INSERT INTO `kb_date` VALUES ('2025-08-12');
INSERT INTO `kb_date` VALUES ('2025-08-13');
INSERT INTO `kb_date` VALUES ('2025-08-14');
INSERT INTO `kb_date` VALUES ('2025-08-15');
INSERT INTO `kb_date` VALUES ('2025-08-16');
INSERT INTO `kb_date` VALUES ('2025-08-17');
INSERT INTO `kb_date` VALUES ('2025-08-18');
INSERT INTO `kb_date` VALUES ('2025-08-19');
INSERT INTO `kb_date` VALUES ('2025-08-20');
INSERT INTO `kb_date` VALUES ('2025-08-21');
INSERT INTO `kb_date` VALUES ('2025-08-22');
INSERT INTO `kb_date` VALUES ('2025-08-23');
INSERT INTO `kb_date` VALUES ('2025-08-24');
INSERT INTO `kb_date` VALUES ('2025-08-25');
INSERT INTO `kb_date` VALUES ('2025-08-26');
INSERT INTO `kb_date` VALUES ('2025-08-27');
INSERT INTO `kb_date` VALUES ('2025-08-28');
INSERT INTO `kb_date` VALUES ('2025-08-29');
INSERT INTO `kb_date` VALUES ('2025-08-30');
INSERT INTO `kb_date` VALUES ('2025-08-31');
INSERT INTO `kb_date` VALUES ('2025-09-01');
INSERT INTO `kb_date` VALUES ('2025-09-02');
INSERT INTO `kb_date` VALUES ('2025-09-03');
INSERT INTO `kb_date` VALUES ('2025-09-04');
INSERT INTO `kb_date` VALUES ('2025-09-05');
INSERT INTO `kb_date` VALUES ('2025-09-06');
INSERT INTO `kb_date` VALUES ('2025-09-07');
INSERT INTO `kb_date` VALUES ('2025-09-08');
INSERT INTO `kb_date` VALUES ('2025-09-09');
INSERT INTO `kb_date` VALUES ('2025-09-10');
INSERT INTO `kb_date` VALUES ('2025-09-11');
INSERT INTO `kb_date` VALUES ('2025-09-12');
INSERT INTO `kb_date` VALUES ('2025-09-13');
INSERT INTO `kb_date` VALUES ('2025-09-14');
INSERT INTO `kb_date` VALUES ('2025-09-15');
INSERT INTO `kb_date` VALUES ('2025-09-16');
INSERT INTO `kb_date` VALUES ('2025-09-17');
INSERT INTO `kb_date` VALUES ('2025-09-18');
INSERT INTO `kb_date` VALUES ('2025-09-19');
INSERT INTO `kb_date` VALUES ('2025-09-20');
INSERT INTO `kb_date` VALUES ('2025-09-21');
INSERT INTO `kb_date` VALUES ('2025-09-22');
INSERT INTO `kb_date` VALUES ('2025-09-23');
INSERT INTO `kb_date` VALUES ('2025-09-24');
INSERT INTO `kb_date` VALUES ('2025-09-25');
INSERT INTO `kb_date` VALUES ('2025-09-26');
INSERT INTO `kb_date` VALUES ('2025-09-27');
INSERT INTO `kb_date` VALUES ('2025-09-28');
INSERT INTO `kb_date` VALUES ('2025-09-29');
INSERT INTO `kb_date` VALUES ('2025-09-30');
INSERT INTO `kb_date` VALUES ('2025-10-01');
INSERT INTO `kb_date` VALUES ('2025-10-02');
INSERT INTO `kb_date` VALUES ('2025-10-03');
INSERT INTO `kb_date` VALUES ('2025-10-04');
INSERT INTO `kb_date` VALUES ('2025-10-05');
INSERT INTO `kb_date` VALUES ('2025-10-06');
INSERT INTO `kb_date` VALUES ('2025-10-07');
INSERT INTO `kb_date` VALUES ('2025-10-08');
INSERT INTO `kb_date` VALUES ('2025-10-09');
INSERT INTO `kb_date` VALUES ('2025-10-10');
INSERT INTO `kb_date` VALUES ('2025-10-11');
INSERT INTO `kb_date` VALUES ('2025-10-12');
INSERT INTO `kb_date` VALUES ('2025-10-13');
INSERT INTO `kb_date` VALUES ('2025-10-14');
INSERT INTO `kb_date` VALUES ('2025-10-15');
INSERT INTO `kb_date` VALUES ('2025-10-16');
INSERT INTO `kb_date` VALUES ('2025-10-17');
INSERT INTO `kb_date` VALUES ('2025-10-18');
INSERT INTO `kb_date` VALUES ('2025-10-19');
INSERT INTO `kb_date` VALUES ('2025-10-20');
INSERT INTO `kb_date` VALUES ('2025-10-21');
INSERT INTO `kb_date` VALUES ('2025-10-22');
INSERT INTO `kb_date` VALUES ('2025-10-23');
INSERT INTO `kb_date` VALUES ('2025-10-24');
INSERT INTO `kb_date` VALUES ('2025-10-25');
INSERT INTO `kb_date` VALUES ('2025-10-26');
INSERT INTO `kb_date` VALUES ('2025-10-27');
INSERT INTO `kb_date` VALUES ('2025-10-28');
INSERT INTO `kb_date` VALUES ('2025-10-29');
INSERT INTO `kb_date` VALUES ('2025-10-30');
INSERT INTO `kb_date` VALUES ('2025-10-31');
INSERT INTO `kb_date` VALUES ('2025-11-01');
INSERT INTO `kb_date` VALUES ('2025-11-02');
INSERT INTO `kb_date` VALUES ('2025-11-03');
INSERT INTO `kb_date` VALUES ('2025-11-04');
INSERT INTO `kb_date` VALUES ('2025-11-05');
INSERT INTO `kb_date` VALUES ('2025-11-06');
INSERT INTO `kb_date` VALUES ('2025-11-07');
INSERT INTO `kb_date` VALUES ('2025-11-08');
INSERT INTO `kb_date` VALUES ('2025-11-09');
INSERT INTO `kb_date` VALUES ('2025-11-10');
INSERT INTO `kb_date` VALUES ('2025-11-11');
INSERT INTO `kb_date` VALUES ('2025-11-12');
INSERT INTO `kb_date` VALUES ('2025-11-13');
INSERT INTO `kb_date` VALUES ('2025-11-14');
INSERT INTO `kb_date` VALUES ('2025-11-15');
INSERT INTO `kb_date` VALUES ('2025-11-16');
INSERT INTO `kb_date` VALUES ('2025-11-17');
INSERT INTO `kb_date` VALUES ('2025-11-18');
INSERT INTO `kb_date` VALUES ('2025-11-19');
INSERT INTO `kb_date` VALUES ('2025-11-20');
INSERT INTO `kb_date` VALUES ('2025-11-21');
INSERT INTO `kb_date` VALUES ('2025-11-22');
INSERT INTO `kb_date` VALUES ('2025-11-23');
INSERT INTO `kb_date` VALUES ('2025-11-24');
INSERT INTO `kb_date` VALUES ('2025-11-25');
INSERT INTO `kb_date` VALUES ('2025-11-26');
INSERT INTO `kb_date` VALUES ('2025-11-27');
INSERT INTO `kb_date` VALUES ('2025-11-28');
INSERT INTO `kb_date` VALUES ('2025-11-29');
INSERT INTO `kb_date` VALUES ('2025-11-30');
INSERT INTO `kb_date` VALUES ('2025-12-01');
INSERT INTO `kb_date` VALUES ('2025-12-02');
INSERT INTO `kb_date` VALUES ('2025-12-03');
INSERT INTO `kb_date` VALUES ('2025-12-04');
INSERT INTO `kb_date` VALUES ('2025-12-05');
INSERT INTO `kb_date` VALUES ('2025-12-06');
INSERT INTO `kb_date` VALUES ('2025-12-07');
INSERT INTO `kb_date` VALUES ('2025-12-08');
INSERT INTO `kb_date` VALUES ('2025-12-09');
INSERT INTO `kb_date` VALUES ('2025-12-10');
INSERT INTO `kb_date` VALUES ('2025-12-11');
INSERT INTO `kb_date` VALUES ('2025-12-12');
INSERT INTO `kb_date` VALUES ('2025-12-13');
INSERT INTO `kb_date` VALUES ('2025-12-14');
INSERT INTO `kb_date` VALUES ('2025-12-15');
INSERT INTO `kb_date` VALUES ('2025-12-16');
INSERT INTO `kb_date` VALUES ('2025-12-17');
INSERT INTO `kb_date` VALUES ('2025-12-18');
INSERT INTO `kb_date` VALUES ('2025-12-19');
INSERT INTO `kb_date` VALUES ('2025-12-20');
INSERT INTO `kb_date` VALUES ('2025-12-21');
INSERT INTO `kb_date` VALUES ('2025-12-22');
INSERT INTO `kb_date` VALUES ('2025-12-23');
INSERT INTO `kb_date` VALUES ('2025-12-24');
INSERT INTO `kb_date` VALUES ('2025-12-25');
INSERT INTO `kb_date` VALUES ('2025-12-26');
INSERT INTO `kb_date` VALUES ('2025-12-27');
INSERT INTO `kb_date` VALUES ('2025-12-28');
INSERT INTO `kb_date` VALUES ('2025-12-29');
INSERT INTO `kb_date` VALUES ('2025-12-30');
INSERT INTO `kb_date` VALUES ('2025-12-31');
INSERT INTO `kb_date` VALUES ('2026-01-01');
INSERT INTO `kb_date` VALUES ('2026-01-02');
INSERT INTO `kb_date` VALUES ('2026-01-03');
INSERT INTO `kb_date` VALUES ('2026-01-04');
INSERT INTO `kb_date` VALUES ('2026-01-05');
INSERT INTO `kb_date` VALUES ('2026-01-06');
INSERT INTO `kb_date` VALUES ('2026-01-07');
INSERT INTO `kb_date` VALUES ('2026-01-08');
INSERT INTO `kb_date` VALUES ('2026-01-09');
INSERT INTO `kb_date` VALUES ('2026-01-10');
INSERT INTO `kb_date` VALUES ('2026-01-11');
INSERT INTO `kb_date` VALUES ('2026-01-12');
INSERT INTO `kb_date` VALUES ('2026-01-13');
INSERT INTO `kb_date` VALUES ('2026-01-14');
INSERT INTO `kb_date` VALUES ('2026-01-15');
INSERT INTO `kb_date` VALUES ('2026-01-16');
INSERT INTO `kb_date` VALUES ('2026-01-17');
INSERT INTO `kb_date` VALUES ('2026-01-18');
INSERT INTO `kb_date` VALUES ('2026-01-19');
INSERT INTO `kb_date` VALUES ('2026-01-20');
INSERT INTO `kb_date` VALUES ('2026-01-21');
INSERT INTO `kb_date` VALUES ('2026-01-22');
INSERT INTO `kb_date` VALUES ('2026-01-23');
INSERT INTO `kb_date` VALUES ('2026-01-24');
INSERT INTO `kb_date` VALUES ('2026-01-25');
INSERT INTO `kb_date` VALUES ('2026-01-26');
INSERT INTO `kb_date` VALUES ('2026-01-27');
INSERT INTO `kb_date` VALUES ('2026-01-28');
INSERT INTO `kb_date` VALUES ('2026-01-29');
INSERT INTO `kb_date` VALUES ('2026-01-30');
INSERT INTO `kb_date` VALUES ('2026-01-31');
INSERT INTO `kb_date` VALUES ('2026-02-01');
INSERT INTO `kb_date` VALUES ('2026-02-02');
INSERT INTO `kb_date` VALUES ('2026-02-03');
INSERT INTO `kb_date` VALUES ('2026-02-04');
INSERT INTO `kb_date` VALUES ('2026-02-05');
INSERT INTO `kb_date` VALUES ('2026-02-06');
INSERT INTO `kb_date` VALUES ('2026-02-07');
INSERT INTO `kb_date` VALUES ('2026-02-08');
INSERT INTO `kb_date` VALUES ('2026-02-09');
INSERT INTO `kb_date` VALUES ('2026-02-10');
INSERT INTO `kb_date` VALUES ('2026-02-11');
INSERT INTO `kb_date` VALUES ('2026-02-12');
INSERT INTO `kb_date` VALUES ('2026-02-13');
INSERT INTO `kb_date` VALUES ('2026-02-14');
INSERT INTO `kb_date` VALUES ('2026-02-15');
INSERT INTO `kb_date` VALUES ('2026-02-16');
INSERT INTO `kb_date` VALUES ('2026-02-17');
INSERT INTO `kb_date` VALUES ('2026-02-18');
INSERT INTO `kb_date` VALUES ('2026-02-19');
INSERT INTO `kb_date` VALUES ('2026-02-20');
INSERT INTO `kb_date` VALUES ('2026-02-21');
INSERT INTO `kb_date` VALUES ('2026-02-22');
INSERT INTO `kb_date` VALUES ('2026-02-23');
INSERT INTO `kb_date` VALUES ('2026-02-24');
INSERT INTO `kb_date` VALUES ('2026-02-25');
INSERT INTO `kb_date` VALUES ('2026-02-26');
INSERT INTO `kb_date` VALUES ('2026-02-27');
INSERT INTO `kb_date` VALUES ('2026-02-28');
INSERT INTO `kb_date` VALUES ('2026-03-01');
INSERT INTO `kb_date` VALUES ('2026-03-02');
INSERT INTO `kb_date` VALUES ('2026-03-03');
INSERT INTO `kb_date` VALUES ('2026-03-04');
INSERT INTO `kb_date` VALUES ('2026-03-05');
INSERT INTO `kb_date` VALUES ('2026-03-06');
INSERT INTO `kb_date` VALUES ('2026-03-07');
INSERT INTO `kb_date` VALUES ('2026-03-08');
INSERT INTO `kb_date` VALUES ('2026-03-09');
INSERT INTO `kb_date` VALUES ('2026-03-10');
INSERT INTO `kb_date` VALUES ('2026-03-11');
INSERT INTO `kb_date` VALUES ('2026-03-12');
INSERT INTO `kb_date` VALUES ('2026-03-13');
INSERT INTO `kb_date` VALUES ('2026-03-14');
INSERT INTO `kb_date` VALUES ('2026-03-15');
INSERT INTO `kb_date` VALUES ('2026-03-16');
INSERT INTO `kb_date` VALUES ('2026-03-17');
INSERT INTO `kb_date` VALUES ('2026-03-18');
INSERT INTO `kb_date` VALUES ('2026-03-19');
INSERT INTO `kb_date` VALUES ('2026-03-20');
INSERT INTO `kb_date` VALUES ('2026-03-21');
INSERT INTO `kb_date` VALUES ('2026-03-22');
INSERT INTO `kb_date` VALUES ('2026-03-23');
INSERT INTO `kb_date` VALUES ('2026-03-24');
INSERT INTO `kb_date` VALUES ('2026-03-25');
INSERT INTO `kb_date` VALUES ('2026-03-26');
INSERT INTO `kb_date` VALUES ('2026-03-27');
INSERT INTO `kb_date` VALUES ('2026-03-28');
INSERT INTO `kb_date` VALUES ('2026-03-29');
INSERT INTO `kb_date` VALUES ('2026-03-30');
INSERT INTO `kb_date` VALUES ('2026-03-31');
INSERT INTO `kb_date` VALUES ('2026-04-01');
INSERT INTO `kb_date` VALUES ('2026-04-02');
INSERT INTO `kb_date` VALUES ('2026-04-03');
INSERT INTO `kb_date` VALUES ('2026-04-04');
INSERT INTO `kb_date` VALUES ('2026-04-05');
INSERT INTO `kb_date` VALUES ('2026-04-06');
INSERT INTO `kb_date` VALUES ('2026-04-07');
INSERT INTO `kb_date` VALUES ('2026-04-08');
INSERT INTO `kb_date` VALUES ('2026-04-09');
INSERT INTO `kb_date` VALUES ('2026-04-10');
INSERT INTO `kb_date` VALUES ('2026-04-11');
INSERT INTO `kb_date` VALUES ('2026-04-12');
INSERT INTO `kb_date` VALUES ('2026-04-13');
INSERT INTO `kb_date` VALUES ('2026-04-14');
INSERT INTO `kb_date` VALUES ('2026-04-15');
INSERT INTO `kb_date` VALUES ('2026-04-16');
INSERT INTO `kb_date` VALUES ('2026-04-17');
INSERT INTO `kb_date` VALUES ('2026-04-18');
INSERT INTO `kb_date` VALUES ('2026-04-19');
INSERT INTO `kb_date` VALUES ('2026-04-20');
INSERT INTO `kb_date` VALUES ('2026-04-21');
INSERT INTO `kb_date` VALUES ('2026-04-22');
INSERT INTO `kb_date` VALUES ('2026-04-23');
INSERT INTO `kb_date` VALUES ('2026-04-24');
INSERT INTO `kb_date` VALUES ('2026-04-25');
INSERT INTO `kb_date` VALUES ('2026-04-26');
INSERT INTO `kb_date` VALUES ('2026-04-27');
INSERT INTO `kb_date` VALUES ('2026-04-28');
INSERT INTO `kb_date` VALUES ('2026-04-29');
INSERT INTO `kb_date` VALUES ('2026-04-30');
INSERT INTO `kb_date` VALUES ('2026-05-01');
INSERT INTO `kb_date` VALUES ('2026-05-02');
INSERT INTO `kb_date` VALUES ('2026-05-03');
INSERT INTO `kb_date` VALUES ('2026-05-04');
INSERT INTO `kb_date` VALUES ('2026-05-05');
INSERT INTO `kb_date` VALUES ('2026-05-06');
INSERT INTO `kb_date` VALUES ('2026-05-07');
INSERT INTO `kb_date` VALUES ('2026-05-08');
INSERT INTO `kb_date` VALUES ('2026-05-09');
INSERT INTO `kb_date` VALUES ('2026-05-10');
INSERT INTO `kb_date` VALUES ('2026-05-11');
INSERT INTO `kb_date` VALUES ('2026-05-12');
INSERT INTO `kb_date` VALUES ('2026-05-13');
INSERT INTO `kb_date` VALUES ('2026-05-14');
INSERT INTO `kb_date` VALUES ('2026-05-15');
INSERT INTO `kb_date` VALUES ('2026-05-16');
INSERT INTO `kb_date` VALUES ('2026-05-17');
INSERT INTO `kb_date` VALUES ('2026-05-18');
INSERT INTO `kb_date` VALUES ('2026-05-19');
INSERT INTO `kb_date` VALUES ('2026-05-20');
INSERT INTO `kb_date` VALUES ('2026-05-21');
INSERT INTO `kb_date` VALUES ('2026-05-22');
INSERT INTO `kb_date` VALUES ('2026-05-23');
INSERT INTO `kb_date` VALUES ('2026-05-24');
INSERT INTO `kb_date` VALUES ('2026-05-25');
INSERT INTO `kb_date` VALUES ('2026-05-26');
INSERT INTO `kb_date` VALUES ('2026-05-27');
INSERT INTO `kb_date` VALUES ('2026-05-28');
INSERT INTO `kb_date` VALUES ('2026-05-29');
INSERT INTO `kb_date` VALUES ('2026-05-30');
INSERT INTO `kb_date` VALUES ('2026-05-31');
INSERT INTO `kb_date` VALUES ('2026-06-01');
INSERT INTO `kb_date` VALUES ('2026-06-02');
INSERT INTO `kb_date` VALUES ('2026-06-03');
INSERT INTO `kb_date` VALUES ('2026-06-04');
INSERT INTO `kb_date` VALUES ('2026-06-05');
INSERT INTO `kb_date` VALUES ('2026-06-06');
INSERT INTO `kb_date` VALUES ('2026-06-07');
INSERT INTO `kb_date` VALUES ('2026-06-08');
INSERT INTO `kb_date` VALUES ('2026-06-09');
INSERT INTO `kb_date` VALUES ('2026-06-10');
INSERT INTO `kb_date` VALUES ('2026-06-11');
INSERT INTO `kb_date` VALUES ('2026-06-12');
INSERT INTO `kb_date` VALUES ('2026-06-13');
INSERT INTO `kb_date` VALUES ('2026-06-14');
INSERT INTO `kb_date` VALUES ('2026-06-15');
INSERT INTO `kb_date` VALUES ('2026-06-16');
INSERT INTO `kb_date` VALUES ('2026-06-17');
INSERT INTO `kb_date` VALUES ('2026-06-18');
INSERT INTO `kb_date` VALUES ('2026-06-19');
INSERT INTO `kb_date` VALUES ('2026-06-20');
INSERT INTO `kb_date` VALUES ('2026-06-21');
INSERT INTO `kb_date` VALUES ('2026-06-22');
INSERT INTO `kb_date` VALUES ('2026-06-23');
INSERT INTO `kb_date` VALUES ('2026-06-24');
INSERT INTO `kb_date` VALUES ('2026-06-25');
INSERT INTO `kb_date` VALUES ('2026-06-26');
INSERT INTO `kb_date` VALUES ('2026-06-27');
INSERT INTO `kb_date` VALUES ('2026-06-28');
INSERT INTO `kb_date` VALUES ('2026-06-29');
INSERT INTO `kb_date` VALUES ('2026-06-30');
INSERT INTO `kb_date` VALUES ('2026-07-01');
INSERT INTO `kb_date` VALUES ('2026-07-02');
INSERT INTO `kb_date` VALUES ('2026-07-03');
INSERT INTO `kb_date` VALUES ('2026-07-04');
INSERT INTO `kb_date` VALUES ('2026-07-05');
INSERT INTO `kb_date` VALUES ('2026-07-06');
INSERT INTO `kb_date` VALUES ('2026-07-07');
INSERT INTO `kb_date` VALUES ('2026-07-08');
INSERT INTO `kb_date` VALUES ('2026-07-09');
INSERT INTO `kb_date` VALUES ('2026-07-10');
INSERT INTO `kb_date` VALUES ('2026-07-11');
INSERT INTO `kb_date` VALUES ('2026-07-12');
INSERT INTO `kb_date` VALUES ('2026-07-13');
INSERT INTO `kb_date` VALUES ('2026-07-14');
INSERT INTO `kb_date` VALUES ('2026-07-15');
INSERT INTO `kb_date` VALUES ('2026-07-16');
INSERT INTO `kb_date` VALUES ('2026-07-17');
INSERT INTO `kb_date` VALUES ('2026-07-18');
INSERT INTO `kb_date` VALUES ('2026-07-19');
INSERT INTO `kb_date` VALUES ('2026-07-20');
INSERT INTO `kb_date` VALUES ('2026-07-21');
INSERT INTO `kb_date` VALUES ('2026-07-22');
INSERT INTO `kb_date` VALUES ('2026-07-23');
INSERT INTO `kb_date` VALUES ('2026-07-24');
INSERT INTO `kb_date` VALUES ('2026-07-25');
INSERT INTO `kb_date` VALUES ('2026-07-26');
INSERT INTO `kb_date` VALUES ('2026-07-27');
INSERT INTO `kb_date` VALUES ('2026-07-28');
INSERT INTO `kb_date` VALUES ('2026-07-29');
INSERT INTO `kb_date` VALUES ('2026-07-30');
INSERT INTO `kb_date` VALUES ('2026-07-31');
INSERT INTO `kb_date` VALUES ('2026-08-01');
INSERT INTO `kb_date` VALUES ('2026-08-02');
INSERT INTO `kb_date` VALUES ('2026-08-03');
INSERT INTO `kb_date` VALUES ('2026-08-04');
INSERT INTO `kb_date` VALUES ('2026-08-05');
INSERT INTO `kb_date` VALUES ('2026-08-06');
INSERT INTO `kb_date` VALUES ('2026-08-07');
INSERT INTO `kb_date` VALUES ('2026-08-08');
INSERT INTO `kb_date` VALUES ('2026-08-09');
INSERT INTO `kb_date` VALUES ('2026-08-10');
INSERT INTO `kb_date` VALUES ('2026-08-11');
INSERT INTO `kb_date` VALUES ('2026-08-12');
INSERT INTO `kb_date` VALUES ('2026-08-13');
INSERT INTO `kb_date` VALUES ('2026-08-14');
INSERT INTO `kb_date` VALUES ('2026-08-15');
INSERT INTO `kb_date` VALUES ('2026-08-16');
INSERT INTO `kb_date` VALUES ('2026-08-17');
INSERT INTO `kb_date` VALUES ('2026-08-18');
INSERT INTO `kb_date` VALUES ('2026-08-19');
INSERT INTO `kb_date` VALUES ('2026-08-20');
INSERT INTO `kb_date` VALUES ('2026-08-21');
INSERT INTO `kb_date` VALUES ('2026-08-22');
INSERT INTO `kb_date` VALUES ('2026-08-23');
INSERT INTO `kb_date` VALUES ('2026-08-24');
INSERT INTO `kb_date` VALUES ('2026-08-25');
INSERT INTO `kb_date` VALUES ('2026-08-26');
INSERT INTO `kb_date` VALUES ('2026-08-27');
INSERT INTO `kb_date` VALUES ('2026-08-28');
INSERT INTO `kb_date` VALUES ('2026-08-29');
INSERT INTO `kb_date` VALUES ('2026-08-30');
INSERT INTO `kb_date` VALUES ('2026-08-31');
INSERT INTO `kb_date` VALUES ('2026-09-01');
INSERT INTO `kb_date` VALUES ('2026-09-02');
INSERT INTO `kb_date` VALUES ('2026-09-03');
INSERT INTO `kb_date` VALUES ('2026-09-04');
INSERT INTO `kb_date` VALUES ('2026-09-05');
INSERT INTO `kb_date` VALUES ('2026-09-06');
INSERT INTO `kb_date` VALUES ('2026-09-07');
INSERT INTO `kb_date` VALUES ('2026-09-08');
INSERT INTO `kb_date` VALUES ('2026-09-09');
INSERT INTO `kb_date` VALUES ('2026-09-10');
INSERT INTO `kb_date` VALUES ('2026-09-11');
INSERT INTO `kb_date` VALUES ('2026-09-12');
INSERT INTO `kb_date` VALUES ('2026-09-13');
INSERT INTO `kb_date` VALUES ('2026-09-14');
INSERT INTO `kb_date` VALUES ('2026-09-15');
INSERT INTO `kb_date` VALUES ('2026-09-16');
INSERT INTO `kb_date` VALUES ('2026-09-17');
INSERT INTO `kb_date` VALUES ('2026-09-18');
INSERT INTO `kb_date` VALUES ('2026-09-19');
INSERT INTO `kb_date` VALUES ('2026-09-20');
INSERT INTO `kb_date` VALUES ('2026-09-21');
INSERT INTO `kb_date` VALUES ('2026-09-22');
INSERT INTO `kb_date` VALUES ('2026-09-23');
INSERT INTO `kb_date` VALUES ('2026-09-24');
INSERT INTO `kb_date` VALUES ('2026-09-25');
INSERT INTO `kb_date` VALUES ('2026-09-26');
INSERT INTO `kb_date` VALUES ('2026-09-27');
INSERT INTO `kb_date` VALUES ('2026-09-28');
INSERT INTO `kb_date` VALUES ('2026-09-29');
INSERT INTO `kb_date` VALUES ('2026-09-30');
INSERT INTO `kb_date` VALUES ('2026-10-01');
INSERT INTO `kb_date` VALUES ('2026-10-02');
INSERT INTO `kb_date` VALUES ('2026-10-03');
INSERT INTO `kb_date` VALUES ('2026-10-04');
INSERT INTO `kb_date` VALUES ('2026-10-05');
INSERT INTO `kb_date` VALUES ('2026-10-06');
INSERT INTO `kb_date` VALUES ('2026-10-07');
INSERT INTO `kb_date` VALUES ('2026-10-08');
INSERT INTO `kb_date` VALUES ('2026-10-09');
INSERT INTO `kb_date` VALUES ('2026-10-10');
INSERT INTO `kb_date` VALUES ('2026-10-11');
INSERT INTO `kb_date` VALUES ('2026-10-12');
INSERT INTO `kb_date` VALUES ('2026-10-13');
INSERT INTO `kb_date` VALUES ('2026-10-14');
INSERT INTO `kb_date` VALUES ('2026-10-15');
INSERT INTO `kb_date` VALUES ('2026-10-16');
INSERT INTO `kb_date` VALUES ('2026-10-17');
INSERT INTO `kb_date` VALUES ('2026-10-18');
INSERT INTO `kb_date` VALUES ('2026-10-19');
INSERT INTO `kb_date` VALUES ('2026-10-20');
INSERT INTO `kb_date` VALUES ('2026-10-21');
INSERT INTO `kb_date` VALUES ('2026-10-22');
INSERT INTO `kb_date` VALUES ('2026-10-23');
INSERT INTO `kb_date` VALUES ('2026-10-24');
INSERT INTO `kb_date` VALUES ('2026-10-25');
INSERT INTO `kb_date` VALUES ('2026-10-26');
INSERT INTO `kb_date` VALUES ('2026-10-27');
INSERT INTO `kb_date` VALUES ('2026-10-28');
INSERT INTO `kb_date` VALUES ('2026-10-29');
INSERT INTO `kb_date` VALUES ('2026-10-30');
INSERT INTO `kb_date` VALUES ('2026-10-31');
INSERT INTO `kb_date` VALUES ('2026-11-01');
INSERT INTO `kb_date` VALUES ('2026-11-02');
INSERT INTO `kb_date` VALUES ('2026-11-03');
INSERT INTO `kb_date` VALUES ('2026-11-04');
INSERT INTO `kb_date` VALUES ('2026-11-05');
INSERT INTO `kb_date` VALUES ('2026-11-06');
INSERT INTO `kb_date` VALUES ('2026-11-07');
INSERT INTO `kb_date` VALUES ('2026-11-08');
INSERT INTO `kb_date` VALUES ('2026-11-09');
INSERT INTO `kb_date` VALUES ('2026-11-10');
INSERT INTO `kb_date` VALUES ('2026-11-11');
INSERT INTO `kb_date` VALUES ('2026-11-12');
INSERT INTO `kb_date` VALUES ('2026-11-13');
INSERT INTO `kb_date` VALUES ('2026-11-14');
INSERT INTO `kb_date` VALUES ('2026-11-15');
INSERT INTO `kb_date` VALUES ('2026-11-16');
INSERT INTO `kb_date` VALUES ('2026-11-17');
INSERT INTO `kb_date` VALUES ('2026-11-18');
INSERT INTO `kb_date` VALUES ('2026-11-19');
INSERT INTO `kb_date` VALUES ('2026-11-20');
INSERT INTO `kb_date` VALUES ('2026-11-21');
INSERT INTO `kb_date` VALUES ('2026-11-22');
INSERT INTO `kb_date` VALUES ('2026-11-23');
INSERT INTO `kb_date` VALUES ('2026-11-24');
INSERT INTO `kb_date` VALUES ('2026-11-25');
INSERT INTO `kb_date` VALUES ('2026-11-26');
INSERT INTO `kb_date` VALUES ('2026-11-27');
INSERT INTO `kb_date` VALUES ('2026-11-28');
INSERT INTO `kb_date` VALUES ('2026-11-29');
INSERT INTO `kb_date` VALUES ('2026-11-30');
INSERT INTO `kb_date` VALUES ('2026-12-01');
INSERT INTO `kb_date` VALUES ('2026-12-02');
INSERT INTO `kb_date` VALUES ('2026-12-03');
INSERT INTO `kb_date` VALUES ('2026-12-04');
INSERT INTO `kb_date` VALUES ('2026-12-05');
INSERT INTO `kb_date` VALUES ('2026-12-06');
INSERT INTO `kb_date` VALUES ('2026-12-07');
INSERT INTO `kb_date` VALUES ('2026-12-08');
INSERT INTO `kb_date` VALUES ('2026-12-09');
INSERT INTO `kb_date` VALUES ('2026-12-10');
INSERT INTO `kb_date` VALUES ('2026-12-11');
INSERT INTO `kb_date` VALUES ('2026-12-12');
INSERT INTO `kb_date` VALUES ('2026-12-13');
INSERT INTO `kb_date` VALUES ('2026-12-14');
INSERT INTO `kb_date` VALUES ('2026-12-15');
INSERT INTO `kb_date` VALUES ('2026-12-16');
INSERT INTO `kb_date` VALUES ('2026-12-17');
INSERT INTO `kb_date` VALUES ('2026-12-18');
INSERT INTO `kb_date` VALUES ('2026-12-19');
INSERT INTO `kb_date` VALUES ('2026-12-20');
INSERT INTO `kb_date` VALUES ('2026-12-21');
INSERT INTO `kb_date` VALUES ('2026-12-22');
INSERT INTO `kb_date` VALUES ('2026-12-23');
INSERT INTO `kb_date` VALUES ('2026-12-24');
INSERT INTO `kb_date` VALUES ('2026-12-25');
INSERT INTO `kb_date` VALUES ('2026-12-26');
INSERT INTO `kb_date` VALUES ('2026-12-27');
INSERT INTO `kb_date` VALUES ('2026-12-28');
INSERT INTO `kb_date` VALUES ('2026-12-29');
INSERT INTO `kb_date` VALUES ('2026-12-30');
INSERT INTO `kb_date` VALUES ('2026-12-31');
INSERT INTO `kb_date` VALUES ('2027-01-01');
INSERT INTO `kb_date` VALUES ('2027-01-02');
INSERT INTO `kb_date` VALUES ('2027-01-03');
INSERT INTO `kb_date` VALUES ('2027-01-04');
INSERT INTO `kb_date` VALUES ('2027-01-05');
INSERT INTO `kb_date` VALUES ('2027-01-06');
INSERT INTO `kb_date` VALUES ('2027-01-07');
INSERT INTO `kb_date` VALUES ('2027-01-08');
INSERT INTO `kb_date` VALUES ('2027-01-09');
INSERT INTO `kb_date` VALUES ('2027-01-10');
INSERT INTO `kb_date` VALUES ('2027-01-11');
INSERT INTO `kb_date` VALUES ('2027-01-12');
INSERT INTO `kb_date` VALUES ('2027-01-13');
INSERT INTO `kb_date` VALUES ('2027-01-14');
INSERT INTO `kb_date` VALUES ('2027-01-15');
INSERT INTO `kb_date` VALUES ('2027-01-16');
INSERT INTO `kb_date` VALUES ('2027-01-17');
INSERT INTO `kb_date` VALUES ('2027-01-18');
INSERT INTO `kb_date` VALUES ('2027-01-19');
INSERT INTO `kb_date` VALUES ('2027-01-20');
INSERT INTO `kb_date` VALUES ('2027-01-21');
INSERT INTO `kb_date` VALUES ('2027-01-22');
INSERT INTO `kb_date` VALUES ('2027-01-23');
INSERT INTO `kb_date` VALUES ('2027-01-24');
INSERT INTO `kb_date` VALUES ('2027-01-25');
INSERT INTO `kb_date` VALUES ('2027-01-26');
INSERT INTO `kb_date` VALUES ('2027-01-27');
INSERT INTO `kb_date` VALUES ('2027-01-28');
INSERT INTO `kb_date` VALUES ('2027-01-29');
INSERT INTO `kb_date` VALUES ('2027-01-30');
INSERT INTO `kb_date` VALUES ('2027-01-31');
INSERT INTO `kb_date` VALUES ('2027-02-01');
INSERT INTO `kb_date` VALUES ('2027-02-02');
INSERT INTO `kb_date` VALUES ('2027-02-03');
INSERT INTO `kb_date` VALUES ('2027-02-04');
INSERT INTO `kb_date` VALUES ('2027-02-05');
INSERT INTO `kb_date` VALUES ('2027-02-06');
INSERT INTO `kb_date` VALUES ('2027-02-07');
INSERT INTO `kb_date` VALUES ('2027-02-08');
INSERT INTO `kb_date` VALUES ('2027-02-09');
INSERT INTO `kb_date` VALUES ('2027-02-10');
INSERT INTO `kb_date` VALUES ('2027-02-11');
INSERT INTO `kb_date` VALUES ('2027-02-12');
INSERT INTO `kb_date` VALUES ('2027-02-13');
INSERT INTO `kb_date` VALUES ('2027-02-14');
INSERT INTO `kb_date` VALUES ('2027-02-15');
INSERT INTO `kb_date` VALUES ('2027-02-16');
INSERT INTO `kb_date` VALUES ('2027-02-17');
INSERT INTO `kb_date` VALUES ('2027-02-18');
INSERT INTO `kb_date` VALUES ('2027-02-19');
INSERT INTO `kb_date` VALUES ('2027-02-20');
INSERT INTO `kb_date` VALUES ('2027-02-21');
INSERT INTO `kb_date` VALUES ('2027-02-22');
INSERT INTO `kb_date` VALUES ('2027-02-23');
INSERT INTO `kb_date` VALUES ('2027-02-24');
INSERT INTO `kb_date` VALUES ('2027-02-25');
INSERT INTO `kb_date` VALUES ('2027-02-26');
INSERT INTO `kb_date` VALUES ('2027-02-27');
INSERT INTO `kb_date` VALUES ('2027-02-28');
INSERT INTO `kb_date` VALUES ('2027-03-01');
INSERT INTO `kb_date` VALUES ('2027-03-02');
INSERT INTO `kb_date` VALUES ('2027-03-03');
INSERT INTO `kb_date` VALUES ('2027-03-04');
INSERT INTO `kb_date` VALUES ('2027-03-05');
INSERT INTO `kb_date` VALUES ('2027-03-06');
INSERT INTO `kb_date` VALUES ('2027-03-07');
INSERT INTO `kb_date` VALUES ('2027-03-08');
INSERT INTO `kb_date` VALUES ('2027-03-09');
INSERT INTO `kb_date` VALUES ('2027-03-10');
INSERT INTO `kb_date` VALUES ('2027-03-11');
INSERT INTO `kb_date` VALUES ('2027-03-12');
INSERT INTO `kb_date` VALUES ('2027-03-13');
INSERT INTO `kb_date` VALUES ('2027-03-14');
INSERT INTO `kb_date` VALUES ('2027-03-15');
INSERT INTO `kb_date` VALUES ('2027-03-16');
INSERT INTO `kb_date` VALUES ('2027-03-17');
INSERT INTO `kb_date` VALUES ('2027-03-18');
INSERT INTO `kb_date` VALUES ('2027-03-19');
INSERT INTO `kb_date` VALUES ('2027-03-20');
INSERT INTO `kb_date` VALUES ('2027-03-21');
INSERT INTO `kb_date` VALUES ('2027-03-22');
INSERT INTO `kb_date` VALUES ('2027-03-23');
INSERT INTO `kb_date` VALUES ('2027-03-24');
INSERT INTO `kb_date` VALUES ('2027-03-25');
INSERT INTO `kb_date` VALUES ('2027-03-26');
INSERT INTO `kb_date` VALUES ('2027-03-27');
INSERT INTO `kb_date` VALUES ('2027-03-28');
INSERT INTO `kb_date` VALUES ('2027-03-29');
INSERT INTO `kb_date` VALUES ('2027-03-30');
INSERT INTO `kb_date` VALUES ('2027-03-31');
INSERT INTO `kb_date` VALUES ('2027-04-01');
INSERT INTO `kb_date` VALUES ('2027-04-02');
INSERT INTO `kb_date` VALUES ('2027-04-03');
INSERT INTO `kb_date` VALUES ('2027-04-04');
INSERT INTO `kb_date` VALUES ('2027-04-05');
INSERT INTO `kb_date` VALUES ('2027-04-06');
INSERT INTO `kb_date` VALUES ('2027-04-07');
INSERT INTO `kb_date` VALUES ('2027-04-08');
INSERT INTO `kb_date` VALUES ('2027-04-09');
INSERT INTO `kb_date` VALUES ('2027-04-10');
INSERT INTO `kb_date` VALUES ('2027-04-11');
INSERT INTO `kb_date` VALUES ('2027-04-12');
INSERT INTO `kb_date` VALUES ('2027-04-13');
INSERT INTO `kb_date` VALUES ('2027-04-14');
INSERT INTO `kb_date` VALUES ('2027-04-15');
INSERT INTO `kb_date` VALUES ('2027-04-16');
INSERT INTO `kb_date` VALUES ('2027-04-17');
INSERT INTO `kb_date` VALUES ('2027-04-18');
INSERT INTO `kb_date` VALUES ('2027-04-19');
INSERT INTO `kb_date` VALUES ('2027-04-20');
INSERT INTO `kb_date` VALUES ('2027-04-21');
INSERT INTO `kb_date` VALUES ('2027-04-22');
INSERT INTO `kb_date` VALUES ('2027-04-23');
INSERT INTO `kb_date` VALUES ('2027-04-24');
INSERT INTO `kb_date` VALUES ('2027-04-25');
INSERT INTO `kb_date` VALUES ('2027-04-26');
INSERT INTO `kb_date` VALUES ('2027-04-27');
INSERT INTO `kb_date` VALUES ('2027-04-28');
INSERT INTO `kb_date` VALUES ('2027-04-29');
INSERT INTO `kb_date` VALUES ('2027-04-30');
INSERT INTO `kb_date` VALUES ('2027-05-01');
INSERT INTO `kb_date` VALUES ('2027-05-02');
INSERT INTO `kb_date` VALUES ('2027-05-03');
INSERT INTO `kb_date` VALUES ('2027-05-04');
INSERT INTO `kb_date` VALUES ('2027-05-05');
INSERT INTO `kb_date` VALUES ('2027-05-06');
INSERT INTO `kb_date` VALUES ('2027-05-07');
INSERT INTO `kb_date` VALUES ('2027-05-08');
INSERT INTO `kb_date` VALUES ('2027-05-09');
INSERT INTO `kb_date` VALUES ('2027-05-10');
INSERT INTO `kb_date` VALUES ('2027-05-11');
INSERT INTO `kb_date` VALUES ('2027-05-12');
INSERT INTO `kb_date` VALUES ('2027-05-13');
INSERT INTO `kb_date` VALUES ('2027-05-14');
INSERT INTO `kb_date` VALUES ('2027-05-15');
INSERT INTO `kb_date` VALUES ('2027-05-16');
INSERT INTO `kb_date` VALUES ('2027-05-17');
INSERT INTO `kb_date` VALUES ('2027-05-18');

-- ----------------------------
-- Table structure for kb_download_conf
-- ----------------------------
DROP TABLE IF EXISTS `kb_download_conf`;
CREATE TABLE `kb_download_conf`  (
  `id` bigint NOT NULL COMMENT '主键Id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '类型名称',
  `type` int NOT NULL COMMENT '状态 1: 水印 2: 下载',
  `status` int NOT NULL DEFAULT 1 COMMENT '状态 1：支持  2：不支持',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新人',
  `is_deleted` int NOT NULL DEFAULT 2 COMMENT '1：删除 2：正常',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '文件下载配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_download_conf
-- ----------------------------
INSERT INTO `kb_download_conf` VALUES (1672846649820823553, '下载水印', 1, 2, '知识下载水印', '2023-06-25 13:58:16', 1375360470249771009, '2023-06-25 16:19:25', 1375360470249771009, 2);
INSERT INTO `kb_download_conf` VALUES (1672846862321041409, '源文件下载', 2, 1, '知识下载源文件', '2023-06-25 13:59:07', 1375360470249771009, '2023-06-25 16:19:19', 1375360470249771009, 2);

-- ----------------------------
-- Table structure for kb_duplicate_check
-- ----------------------------
DROP TABLE IF EXISTS `kb_duplicate_check`;
CREATE TABLE `kb_duplicate_check`  (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '主键',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标题',
  `author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `file_suffix` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件后缀',
  `upload_path` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '上传路径',
  `status` int NULL DEFAULT NULL COMMENT '状态：1=未开始，2=处理中，3=转换PDF中，4=成功，5=失败',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '检测类型',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `pdf_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'PDF转换成功后地址',
  `detection_time` datetime NULL DEFAULT NULL COMMENT '检测时间',
  `deadline_time` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建用户',
  `html_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'html原文地址',
  `weight` int NULL DEFAULT NULL COMMENT '权重',
  `context_length` int NULL DEFAULT NULL COMMENT '上下文长度',
  `max_number_contiguous` int NULL DEFAULT NULL COMMENT '最大连续数',
  `read_data_length` int NULL DEFAULT NULL COMMENT '读取数据长度',
  `estimated_end_time` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '预计结束时间',
  `upload_type` int NULL DEFAULT 1 COMMENT '1：查重，2文件对比',
  `contrast_path` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '对比路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识库查重表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_duplicate_check
-- ----------------------------

-- ----------------------------
-- Table structure for kb_groups
-- ----------------------------
DROP TABLE IF EXISTS `kb_groups`;
CREATE TABLE `kb_groups`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '群组名称',
  `p_id` bigint NULL DEFAULT NULL COMMENT '父id',
  `sort` int NULL DEFAULT NULL COMMENT '排序号',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes 生效；no 失效',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes 删除；no 未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识库用户组管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_groups
-- ----------------------------
INSERT INTO `kb_groups` VALUES (1704054353620643842, '技术部门', NULL, 200, NULL, '2023-09-19 16:46:32', 1375360470249771009, 1375360470249771009, '2023-09-19 16:47:10', 2);
INSERT INTO `kb_groups` VALUES (1704054480250875906, '商务独立群组', NULL, 50, NULL, '2023-09-19 16:47:03', 1375360470249771009, 1375360470249771009, '2023-09-28 11:48:09', 2);
INSERT INTO `kb_groups` VALUES (1704054690553278465, '后端', 1704054353620643842, 100, NULL, '2023-09-19 16:47:53', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_groups` VALUES (1704430581687099393, '前端', 1704054353620643842, 99, NULL, '2023-09-20 17:41:32', 1375360470249771009, 1375360470249771009, '2023-09-20 17:41:53', 2);
INSERT INTO `kb_groups` VALUES (1704430625005871106, '设计', 1704054353620643842, 98, NULL, '2023-09-20 17:41:42', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_groups` VALUES (1705045210071056385, '测试', 1704054353620643842, 1, NULL, '2023-09-22 10:23:51', 1375360470249771009, NULL, NULL, 2);
INSERT INTO `kb_groups` VALUES (1706138082748923906, '测试要删', NULL, 11, NULL, '2023-09-25 10:46:32', 1706134440184672257, 1706134440184672257, '2023-09-25 10:46:43', 2);
INSERT INTO `kb_groups` VALUES (1707240858858041345, '一组', 1704054480250875906, 1, NULL, '2023-09-28 11:48:34', 1375360470249771009, NULL, NULL, 2);

-- ----------------------------
-- Table structure for kb_groups_user
-- ----------------------------
DROP TABLE IF EXISTS `kb_groups_user`;
CREATE TABLE `kb_groups_user`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `group_id` bigint NULL DEFAULT NULL COMMENT '用户群组ID',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户ID',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识库用户组与用户关联关系管理' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_groups_user
-- ----------------------------
INSERT INTO `kb_groups_user` VALUES (1704056539725443074, 1704054690553278465, 1703968272694964226);
INSERT INTO `kb_groups_user` VALUES (1704056539956129793, 1704054690553278465, 1702581691933278210);
INSERT INTO `kb_groups_user` VALUES (1707272776035086337, 1707240858858041345, 1707241195564183553);

-- ----------------------------
-- Table structure for kb_label_category
-- ----------------------------
DROP TABLE IF EXISTS `kb_label_category`;
CREATE TABLE `kb_label_category`  (
  `id` bigint NOT NULL COMMENT '主键Id',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签分类名称',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除',
  `status` int NULL DEFAULT NULL COMMENT '是否启用',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新用户',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签分类表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_label_category
-- ----------------------------
INSERT INTO `kb_label_category` VALUES (1706211559438848001, '机器人', '2023-09-25 15:38:30', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211559464013826, '物联网', '2023-09-25 15:38:30', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211559464013827, '无人机', '2023-09-25 15:38:30', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211559464013828, '人工智能', '2023-09-25 15:38:30', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211559468208130, 'SAAS', '2023-09-25 15:38:30', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211559468208131, '大数据', '2023-09-25 15:38:30', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211559468208132, '农业', '2023-09-25 15:38:30', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211559468208133, '教育', '2023-09-25 15:38:30', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211559468208134, '金融', '2023-09-25 15:38:30', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211559476596737, '汽车交通', '2023-09-25 15:38:30', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211736878878722, '医疗健康', '2023-09-25 15:39:13', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211736908238849, '旅游', '2023-09-25 15:39:13', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211736912433153, '广告营销', '2023-09-25 15:39:13', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211736912433154, '硬件', '2023-09-25 15:39:13', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211736912433155, '企业服务', '2023-09-25 15:39:13', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211736912433156, '电商零售', '2023-09-25 15:39:13', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211736912433157, '工具软件', '2023-09-25 15:39:13', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211736912433158, '消费升级', '2023-09-25 15:39:13', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211736912433159, '文化娱乐', '2023-09-25 15:39:13', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211736912433160, '物流', '2023-09-25 15:39:13', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211911802327042, '区块链', '2023-09-25 15:39:54', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211911869435906, '生活服务', '2023-09-25 15:39:54', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211911869435907, '体育', '2023-09-25 15:39:54', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211911869435908, '社交', '2023-09-25 15:39:54', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211911869435909, '房产家居', '2023-09-25 15:39:54', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211911869435910, 'VR/AR', '2023-09-25 15:39:54', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211911869435911, '材料', '2023-09-25 15:39:54', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211911869435912, '餐饮业', '2023-09-25 15:39:54', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211911869435913, '公用事业', '2023-09-25 15:39:54', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706211911869435914, '环保产业', '2023-09-25 15:39:54', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706212016714452994, '能源矿产', '2023-09-25 15:40:19', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706212016748007425, '半导体', '2023-09-25 15:40:19', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706212016748007426, '游戏/电竞', '2023-09-25 15:40:19', 2, 1, 1375360470249771009, NULL, NULL);
INSERT INTO `kb_label_category` VALUES (1706212016748007427, '元宇宙', '2023-09-25 15:40:19', 2, 1, 1375360470249771009, NULL, NULL);

-- ----------------------------
-- Table structure for kb_model
-- ----------------------------
DROP TABLE IF EXISTS `kb_model`;
CREATE TABLE `kb_model`  (
  `id` bigint NOT NULL COMMENT '主键 ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模型名字',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性编码（用于代码中引用）',
  `sort` int NULL DEFAULT NULL COMMENT '序号',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes生效；no失效',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否已删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模型表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_model
-- ----------------------------
INSERT INTO `kb_model` VALUES (1707242932215754754, '数据库', 'xgzokmz', NULL, 1, 1375360470249771009, '2023-09-28 11:56:49', 1375360470249771009, '2023-09-28 11:56:52', 2);
INSERT INTO `kb_model` VALUES (1707271818831998978, '人工智能', 'ufwbirq', NULL, 1, 1375360470249771009, '2023-09-28 13:51:36', 1375360470249771009, '2023-09-28 14:29:20', 2);

-- ----------------------------
-- Table structure for kb_model_dict
-- ----------------------------
DROP TABLE IF EXISTS `kb_model_dict`;
CREATE TABLE `kb_model_dict`  (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字典名字',
  `pid` int NULL DEFAULT NULL COMMENT '父级字典id,默认0',
  `sort` int NULL DEFAULT NULL COMMENT '字典排序',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes 生效；no 失效',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes 删除；no 未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '字典表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_model_dict
-- ----------------------------
INSERT INTO `kb_model_dict` VALUES (1, '性别', 0, 1, 1, NULL, 1, 1, '2023-04-20 13:09:19', 2);
INSERT INTO `kb_model_dict` VALUES (2, '男', 1, 1, 1, NULL, 1, 1, '2023-04-20 13:10:24', 2);
INSERT INTO `kb_model_dict` VALUES (3, '女', 1, 1, 1, NULL, 1, 1, '2023-04-20 13:10:24', 2);
INSERT INTO `kb_model_dict` VALUES (4, '类型', 0, 1, 1, NULL, 1, 1, '2023-04-20 13:11:12', 2);
INSERT INTO `kb_model_dict` VALUES (5, '类型1', 4, 1, 1, NULL, 1, 1, '2023-04-20 13:11:58', 2);
INSERT INTO `kb_model_dict` VALUES (6, '类型2', 4, 1, 1, NULL, 1, 1, '2023-04-20 13:11:58', 2);
INSERT INTO `kb_model_dict` VALUES (7, '类型3', 4, 1, 1, NULL, 1, 1, '2023-04-20 13:11:58', 2);

-- ----------------------------
-- Table structure for kb_model_domain
-- ----------------------------
DROP TABLE IF EXISTS `kb_model_domain`;
CREATE TABLE `kb_model_domain`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '库名称',
  `url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '值域地址',
  `show_type` int NULL DEFAULT NULL COMMENT '1:树形 2:列表',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1649028910618365954 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '值域管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_model_domain
-- ----------------------------
INSERT INTO `kb_model_domain` VALUES (1, '性别', '/system/dict/listByCode?code=sex', NULL, '性别选项', NULL);

-- ----------------------------
-- Table structure for kb_model_ks_history_ufwbirq
-- ----------------------------
DROP TABLE IF EXISTS `kb_model_ks_history_ufwbirq`;
CREATE TABLE `kb_model_ks_history_ufwbirq`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` bigint NULL DEFAULT NULL COMMENT 'data_id',
  `col_1` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '所属领域',
  `col_2` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '类型',
  `col_3` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '是否发表',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `kb_model_ks_history_ufwbirq`(`data_id` ASC) USING BTREE,
  CONSTRAINT `kb_model_ks_history_ufwbirq` FOREIGN KEY (`data_id`) REFERENCES `kb_basic_history` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人工智能' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_model_ks_history_ufwbirq
-- ----------------------------

-- ----------------------------
-- Table structure for kb_model_ks_history_xabyxep
-- ----------------------------
DROP TABLE IF EXISTS `kb_model_ks_history_xabyxep`;
CREATE TABLE `kb_model_ks_history_xabyxep`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` bigint NULL DEFAULT NULL COMMENT 'data_id',
  `col_1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '12',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `kb_model_ks_history_xabyxep`(`data_id` ASC) USING BTREE,
  CONSTRAINT `kb_model_ks_history_xabyxep` FOREIGN KEY (`data_id`) REFERENCES `kb_basic_history` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '12' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_model_ks_history_xabyxep
-- ----------------------------

-- ----------------------------
-- Table structure for kb_model_ks_history_xgzokmz
-- ----------------------------
DROP TABLE IF EXISTS `kb_model_ks_history_xgzokmz`;
CREATE TABLE `kb_model_ks_history_xgzokmz`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` bigint NULL DEFAULT NULL COMMENT 'data_id',
  `col_1` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据库类型',
  `col_2` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '是否国产',
  `col_3` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据库应用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `kb_model_ks_history_xgzokmz`(`data_id` ASC) USING BTREE,
  CONSTRAINT `kb_model_ks_history_xgzokmz` FOREIGN KEY (`data_id`) REFERENCES `kb_basic_history` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据库' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_model_ks_history_xgzokmz
-- ----------------------------

-- ----------------------------
-- Table structure for kb_model_ks_ufwbirq
-- ----------------------------
DROP TABLE IF EXISTS `kb_model_ks_ufwbirq`;
CREATE TABLE `kb_model_ks_ufwbirq`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` bigint NULL DEFAULT NULL COMMENT 'data_id',
  `col_1` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '所属领域',
  `col_2` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '类型',
  `col_3` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '是否发表',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `kb_model_ks_ufwbirq`(`data_id` ASC) USING BTREE,
  CONSTRAINT `kb_model_ks_ufwbirq` FOREIGN KEY (`data_id`) REFERENCES `kb_basic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 7 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '人工智能' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_model_ks_ufwbirq
-- ----------------------------
INSERT INTO `kb_model_ks_ufwbirq` VALUES (1, 1707281199984304129, '数据挖掘', '专利', '是');
INSERT INTO `kb_model_ks_ufwbirq` VALUES (2, 1707283097286107138, 'NLP自然语言处理', '专利', '是');
INSERT INTO `kb_model_ks_ufwbirq` VALUES (3, 1707283791124652033, '数据挖掘', '专利', '是');
INSERT INTO `kb_model_ks_ufwbirq` VALUES (4, 1707284634775347201, '数据挖掘', '专利', '是');
INSERT INTO `kb_model_ks_ufwbirq` VALUES (5, 1707285522684338177, 'NLP自然语言处理', '专利', '是');
INSERT INTO `kb_model_ks_ufwbirq` VALUES (6, 1707287099046387714, 'NLP自然语言处理', '专利', '是');

-- ----------------------------
-- Table structure for kb_model_ks_xabyxep
-- ----------------------------
DROP TABLE IF EXISTS `kb_model_ks_xabyxep`;
CREATE TABLE `kb_model_ks_xabyxep`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` bigint NULL DEFAULT NULL COMMENT 'data_id',
  `col_1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '12',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `kb_model_ks_xabyxep`(`data_id` ASC) USING BTREE,
  CONSTRAINT `kb_model_ks_xabyxep` FOREIGN KEY (`data_id`) REFERENCES `kb_basic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '12' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_model_ks_xabyxep
-- ----------------------------

-- ----------------------------
-- Table structure for kb_model_ks_xgzokmz
-- ----------------------------
DROP TABLE IF EXISTS `kb_model_ks_xgzokmz`;
CREATE TABLE `kb_model_ks_xgzokmz`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'id',
  `data_id` bigint NULL DEFAULT NULL COMMENT 'data_id',
  `col_1` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据库类型',
  `col_2` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '是否国产',
  `col_3` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据库应用',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `kb_model_ks_xgzokmz`(`data_id` ASC) USING BTREE,
  CONSTRAINT `kb_model_ks_xgzokmz` FOREIGN KEY (`data_id`) REFERENCES `kb_basic` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '数据库' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_model_ks_xgzokmz
-- ----------------------------
INSERT INTO `kb_model_ks_xgzokmz` VALUES (1, 1707290070165499906, '非关系型数据库', '否', 'Elasticsearch');
INSERT INTO `kb_model_ks_xgzokmz` VALUES (2, 1925033072968441857, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for kb_model_label
-- ----------------------------
DROP TABLE IF EXISTS `kb_model_label`;
CREATE TABLE `kb_model_label`  (
  `id` bigint NOT NULL COMMENT '主键',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名',
  `status` int NULL DEFAULT NULL COMMENT '是否机选标签 1：机选标签  2：手动标签',
  `sort` int NULL DEFAULT NULL COMMENT '排序号',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否已删除',
  `hot_word` bigint NULL DEFAULT 2 COMMENT '标签状态  1：热词   2：非热词',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '标签管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_model_label
-- ----------------------------

-- ----------------------------
-- Table structure for kb_property_design
-- ----------------------------
DROP TABLE IF EXISTS `kb_property_design`;
CREATE TABLE `kb_property_design`  (
  `id` bigint NOT NULL COMMENT '主键 ID',
  `model_id` bigint NULL DEFAULT NULL COMMENT '与模型表的关联字段',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性标题',
  `prop_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性编码（用于代码中引用）',
  `sort` int NULL DEFAULT NULL COMMENT '序号',
  `is_required` int NULL DEFAULT NULL COMMENT '是否为必填项，数据取自枚举YesNoEnum',
  `attachment` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '附件',
  `is_filtrate` int NULL DEFAULT NULL COMMENT '是否为筛选项，数据取自枚举YesNoEnum',
  `data_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '整形、字符串',
  `form_type` int NULL DEFAULT NULL COMMENT '枚举类型表单类型（短文本、文本、下拉框等）',
  `status` int NULL DEFAULT NULL COMMENT '数据取自枚举YesNoEnum',
  `select_frame` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '下拉框',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '数据取自枚举YesNoEnum是否删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '模型属性设计表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_property_design
-- ----------------------------
INSERT INTO `kb_property_design` VALUES (1707242932417081345, 1707242932215754754, '数据库类型', 'col_1', NULL, 2, NULL, 1, 'longtext', 5, 1, '[{\"name\":\"关系型数据库\"},{\"name\":\"非关系型数据库\"},{\"name\":\"NoSQL数据库\"},{\"name\":\"图数据库\"}]', NULL, NULL, NULL, '2023-09-28 11:56:49', 2);
INSERT INTO `kb_property_design` VALUES (1707242932417081346, 1707242932215754754, '是否国产', 'col_2', NULL, 2, NULL, 1, 'longtext', 5, 1, '[{\"name\":\"是\"},{\"name\":\"否\"}]', NULL, NULL, NULL, '2023-09-28 11:56:49', 2);
INSERT INTO `kb_property_design` VALUES (1707242932417081347, 1707242932215754754, '数据库应用', 'col_3', NULL, 2, NULL, 1, 'longtext', 5, 1, '[{\"name\":\"MySql\"},{\"name\":\"SQLServer\"},{\"name\":\"MongDB\"},{\"name\":\"Redis\"},{\"name\":\"Hive\"},{\"name\":\"PostgreSQL\"},{\"name\":\"Oracle\"},{\"name\":\"SQLite\"},{\"name\":\"Elasticsearch\"},{\"name\":\"MariaDB\"}]', NULL, NULL, NULL, '2023-09-28 11:56:49', 2);
INSERT INTO `kb_property_design` VALUES (1707281317512896514, 1707271818831998978, '所属领域', 'col_1', NULL, 2, NULL, 1, 'longtext', 5, 1, '[{\"name\":\"NLP自然语言处理\"},{\"name\":\"计算机视觉\"},{\"name\":\"智能医疗\"},{\"name\":\"智能驾驶\"},{\"name\":\"智能机器人\"},{\"name\":\"数据挖掘\"},{\"name\":\"图像识别\"},{\"name\":\"智慧城市及物联网\"}]', NULL, NULL, NULL, '2023-09-28 14:29:21', 2);
INSERT INTO `kb_property_design` VALUES (1707281317512896515, 1707271818831998978, '类型', 'col_2', NULL, 2, NULL, 1, 'longtext', 5, 1, '[{\"name\":\"专利\"},{\"name\":\"论文\"},{\"name\":\"期刊\"}]', NULL, NULL, NULL, '2023-09-28 14:29:21', 2);
INSERT INTO `kb_property_design` VALUES (1707281317512896516, 1707271818831998978, '是否发表', 'col_3', NULL, 2, NULL, 1, 'longtext', 5, 1, '[{\"name\":\"是\"},{\"name\":\"否\"}]', NULL, NULL, NULL, '2023-09-28 14:29:21', 2);

-- ----------------------------
-- Table structure for kb_subject_warehouse
-- ----------------------------
DROP TABLE IF EXISTS `kb_subject_warehouse`;
CREATE TABLE `kb_subject_warehouse`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT '仓库名称',
  `model_id` bigint NULL DEFAULT NULL COMMENT '模型id',
  `is_show` int NULL DEFAULT NULL COMMENT '是否首页显示',
  `cover` varchar(2555) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面地址',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序号',
  `status` int NULL DEFAULT NULL COMMENT '状态 YesNoEnum。yes 生效；no 失效',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes 删除；no 未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '主题知识仓库' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_subject_warehouse
-- ----------------------------
INSERT INTO `kb_subject_warehouse` VALUES (1656835906222718972, '视频课程库', NULL, 2, 'https://img1.baidu.com/it/u=413643897,2296924942&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500', '企业视频课程库测试', -1, 2, '2023-05-15 09:51:56', -1, 1375360470249771009, '2023-09-28 16:03:15', 2);
INSERT INTO `kb_subject_warehouse` VALUES (1707243486069403649, '数据库', 1707242932215754754, 1, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695873491192.png', 'SQL是设计用于在关系数据库管理系统中管理数据的标准语言。', 9, 1, '2023-09-28 11:59:01', 1375360470249771009, 1375360470249771009, '2023-09-28 14:02:59', 2);
INSERT INTO `kb_subject_warehouse` VALUES (1707272674356768770, '人工智能', 1707271818831998978, 1, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/img/1695880479618.png', '人工智能是研究、开发用于模拟、延伸和扩展人的智能的理论、方法、技术及应用系统的一门新的技术科学，是认知、决策、反馈的过程。由于科学技术的逐步提升，实现了更优算法模型、更高算力和大数据', 1, 1, '2023-09-28 13:55:00', 1375360470249771009, 1375360470249771009, '2023-09-28 13:56:43', 2);

-- ----------------------------
-- Table structure for kb_tags
-- ----------------------------
DROP TABLE IF EXISTS `kb_tags`;
CREATE TABLE `kb_tags`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标签名称',
  `weight` bigint NULL DEFAULT NULL COMMENT '权重',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型 00 系统预设 01 提取',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识库标签标' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_tags
-- ----------------------------

-- ----------------------------
-- Table structure for kb_thumbs_up
-- ----------------------------
DROP TABLE IF EXISTS `kb_thumbs_up`;
CREATE TABLE `kb_thumbs_up`  (
  `id` bigint NOT NULL COMMENT '主键',
  `comment_id` bigint NULL DEFAULT NULL COMMENT '评价id',
  `status` int NULL DEFAULT NULL COMMENT '评价状态',
  `basic_id` bigint NULL DEFAULT NULL COMMENT '知识id',
  `user_id` bigint NULL DEFAULT NULL COMMENT '用户id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '知识点赞表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_thumbs_up
-- ----------------------------

-- ----------------------------
-- Table structure for kb_user_label
-- ----------------------------
DROP TABLE IF EXISTS `kb_user_label`;
CREATE TABLE `kb_user_label`  (
  `id` bigint NOT NULL COMMENT '主键',
  `user_id` bigint NOT NULL COMMENT '用户Id',
  `label_id` bigint NOT NULL COMMENT '标签Id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户与表签关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_user_label
-- ----------------------------

-- ----------------------------
-- Table structure for kb_value_def
-- ----------------------------
DROP TABLE IF EXISTS `kb_value_def`;
CREATE TABLE `kb_value_def`  (
  `id` bigint NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '值域名字',
  `url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '值域地址',
  `type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '类型（接口，字典，枚举）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '值域管理表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_value_def
-- ----------------------------

-- ----------------------------
-- Table structure for kb_vide_version
-- ----------------------------
DROP TABLE IF EXISTS `kb_vide_version`;
CREATE TABLE `kb_vide_version`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `b_id` bigint NULL DEFAULT NULL COMMENT '文章id',
  `f_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文章MD5值',
  `access_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '前端访问路径',
  `file_url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件存储路径',
  `file_size` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '文件大小',
  `type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '类型（扩展、基础）',
  `file_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '文件路径',
  `url` json NULL COMMENT '自动生成的URL图片路径',
  `status` int NULL DEFAULT NULL COMMENT '状态：1提取成功、2正在提取中、3：提取失败 ',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除 YesNoEnum。 yes 删除；no 未删除',
  `download_url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件存储下载路径',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = 'PDF/word 等转换成PDF' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_vide_version
-- ----------------------------
INSERT INTO `kb_vide_version` VALUES (1707281202479915010, 1707281199984304129, 'e0588ae5a9ccc69570d052851540be00', 'http://192.168.5.110/', 'C:\\Users\\12965\\Desktop\\测试文件\\GB_T 30948-2021泵站技术管理规程.pdf', '884.22KB', '基础', '词云生成方法及设备.pdf', NULL, 1, '2023-09-28 14:28:53', NULL, NULL, '2023-09-28 14:28:54', 2, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695882391916.pdf');
INSERT INTO `kb_vide_version` VALUES (1707283099580391425, 1707283097286107138, '2cdd7df76c3c0a8dce575bdff3a5d4b1', 'http://192.168.5.110/', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695882776800.pdf', '582.51KB', '基础', '文档标签的生成方法装置终端和存储介质.pdf', NULL, 1, '2023-09-28 14:36:25', NULL, NULL, '2023-09-28 14:36:26', 2, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695882776800.pdf');
INSERT INTO `kb_vide_version` VALUES (1707283793301495809, 1707283791124652033, '4646b3622500c30d43e0e8afa9999f2d', 'http://192.168.5.110/', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883070668.pdf', '628.12KB', '基础', '一种结构化数据版本管理方法与系统.pdf', NULL, 1, '2023-09-28 14:39:11', NULL, NULL, '2023-09-28 14:39:11', 2, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883070668.pdf');
INSERT INTO `kb_vide_version` VALUES (1707284636910247938, 1707284634775347201, '4b9f1655d6d3af906cfa3989ed05345e', 'http://192.168.5.110/', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883176913.pdf', '731.55KB', '基础', '一种基于AI学习的智能防近视系统和方法.pdf', NULL, 1, '2023-09-28 14:42:32', NULL, NULL, '2023-09-28 14:42:32', 2, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883176913.pdf');
INSERT INTO `kb_vide_version` VALUES (1707285525024759810, 1707285522684338177, '4646b3622500c30d43e0e8afa9999f2d', 'http://192.168.5.110/', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883473695.pdf', '628.12KB', '基础', '一种结构化数据版本管理方法与系统.pdf', NULL, 1, '2023-09-28 14:46:04', NULL, NULL, '2023-09-28 14:46:04', 2, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883473695.pdf');
INSERT INTO `kb_vide_version` VALUES (1707287101068042241, 1707287099046387714, 'c461cf51fcae58ffbaf9baa23b27f249', 'http://192.168.5.110/', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883848451.pdf', '1.07MB', '基础', '表格数据的管理方法及装置.pdf', NULL, 1, '2023-09-28 14:52:19', NULL, NULL, '2023-09-28 14:52:20', 2, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695883848451.pdf');
INSERT INTO `kb_vide_version` VALUES (1707290073042792450, 1707290070165499906, 'aa1839b2e4c2661df5c3009a6d476382', 'http://192.168.5.110/', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695884441460.pdf', '1.14MB', '基础', 'ElasticSearch学习文档V2.0.pdf', NULL, 1, '2023-09-28 15:04:08', NULL, NULL, '2023-09-28 15:04:08', 2, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/knowledgeonline/file/1695884441460.pdf');
INSERT INTO `kb_vide_version` VALUES (1925033073450786818, 1925033072968441857, NULL, 'http://nginx/', '/data/knowledgebase/upload/20250521/1747798614887.png', NULL, '基础', 'WeChat.png', NULL, 4, '2025-05-21 11:37:05', NULL, NULL, NULL, 2, NULL);

-- ----------------------------
-- Table structure for kb_video
-- ----------------------------
DROP TABLE IF EXISTS `kb_video`;
CREATE TABLE `kb_video`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `wh_id` bigint NULL DEFAULT NULL COMMENT '主题仓库分类ID',
  `title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT '' COMMENT '标题',
  `auth` bigint NULL DEFAULT NULL COMMENT '作者',
  `dept_id` bigint NULL DEFAULT NULL COMMENT '部门ID',
  `pub_time` datetime NULL DEFAULT NULL COMMENT '发布时间',
  `category_id` bigint NULL DEFAULT NULL COMMENT '知识仓库分类ID',
  `model_id` bigint NULL DEFAULT NULL COMMENT '模型id',
  `exp_time` datetime NULL DEFAULT NULL COMMENT '过期时间',
  `cover` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '封面',
  `video_url` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '视频播放URL',
  `video_src` json NULL COMMENT '视频存储路径',
  `label` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签',
  `model_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '模型编码',
  `remark` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `sort` int NULL DEFAULT NULL COMMENT '排序号',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `is_deleted` int NULL DEFAULT NULL COMMENT '标记删除',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '视频库' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_video
-- ----------------------------

-- ----------------------------
-- Table structure for kb_warehouse_auth
-- ----------------------------
DROP TABLE IF EXISTS `kb_warehouse_auth`;
CREATE TABLE `kb_warehouse_auth`  (
  `id` bigint NOT NULL COMMENT '主键',
  `wh_id` bigint UNSIGNED NOT NULL COMMENT '知识仓库ID',
  `category` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '分类：00知识仓库;01仓库下知识',
  `status` int NULL DEFAULT 1,
  `user_id` bigint NULL DEFAULT NULL COMMENT '人员ID',
  `wh_auth` int NULL DEFAULT NULL COMMENT '知识库权限枚举kb_wh_auth',
  `kb_auth` int NULL DEFAULT NULL COMMENT '知识库下的知识枚举kb_auth',
  `sort` int NULL DEFAULT NULL,
  `is_deleted` int NULL DEFAULT 2,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '主题知识仓库权限表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of kb_warehouse_auth
-- ----------------------------
INSERT INTO `kb_warehouse_auth` VALUES (1702582335813468162, 1702582335540838401, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1702586237623484418, 1702586217629237249, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1702586251645042689, 1702585807652798466, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1703665936558813186, 1703665936265211905, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1703665964501266434, 1703665964228636674, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1703665989490929666, 1703665989230882817, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1703666014119882753, 1703666013855641602, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1703683904437088257, 1703683904185430017, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1703973521363103745, 1702582275675537410, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1703977408312700930, 1702586184771059713, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1704056693799006210, 1703705577445937153, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1704396393235611650, 1702582249742155777, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1704412812903976962, 1704412812572626945, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1704413323329802241, 1703613908948213762, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1704413804026400769, 1704413624917037057, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1705047282468274178, 1705047282178867201, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1705062974731075586, 1705062974450057217, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1705066941498277889, 1705066941183705090, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1706488784323207170, 1706488696783888385, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1706519016472051713, 1703705649080455170, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1706584812728619010, 1706584812481155073, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1706584837655367682, 1706584755828690945, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1706588131392073730, 1706135403796656130, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1706875457645142017, 1706875457276043266, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1706925413856813058, 1706925413554823170, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1706947645484060673, 1706947204645933057, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1706983000325255169, 1706947412649857025, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707212359103422465, 1707212358797238273, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707212415349039106, 1707212201460506626, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707244008012787713, 1707244007706603521, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707244074656083970, 1707244074358288386, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707244173574549505, 1707244173268365314, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707244234454872065, 1707244234148687873, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707244318315786242, 1707244318017990658, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707244367150067714, 1707244366831300609, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707273108102328321, 1707272674356768770, '00', 1, 1375360470249771009, 1, NULL, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707273108484009986, 1707272674356768770, '02', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707274686834171906, 1707243486069403649, '00', 1, 1375360470249771009, 1, NULL, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707274687454928898, 1707243486069403649, '02', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707274858343456770, 1707274858024689665, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707274895597264897, 1707274895203000322, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707274918783377410, 1707274819156074498, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707274959753338881, 1707274959447154690, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707275009623613442, 1707275009330012161, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707275043823968257, 1707275043563921409, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707275082994573314, 1707275082680000514, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707275116247015425, 1707275115961802753, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707275149449125889, 1707275149130358786, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707280013214375937, 1707280012912386049, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707280057086795778, 1707280056801583105, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707280069615181825, 1707244436918120449, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707280105522618369, 1707280105220628482, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707280140033351682, 1707280139727167490, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707280172207857665, 1707280171905867777, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707280206320132098, 1707280206022336514, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707304289661378562, 1707304289250336769, '01', 1, 1375360470249771009, NULL, 5, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707304954466951169, 1656835906222718972, '00', 1, 1375360470249771009, 1, NULL, 0, 2);
INSERT INTO `kb_warehouse_auth` VALUES (1707304954945101826, 1656835906222718972, '02', 1, 1375360470249771009, NULL, 5, 0, 2);

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member`  (
  `id` bigint NOT NULL COMMENT '唯一主键',
  `user_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户名',
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '密码',
  `phone` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '昵称',
  `openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `unionid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信unionid',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `gender` tinyint NULL DEFAULT NULL COMMENT '性别',
  `birthday` date NULL DEFAULT NULL COMMENT '生日',
  `status` tinyint NULL DEFAULT NULL COMMENT '会员状态(正常、禁用、黑名单)',
  `remark` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '备注',
  `tags` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签ids',
  `level_id` bigint NULL DEFAULT NULL COMMENT '等级id',
  `province_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省名称',
  `province_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省代号',
  `city_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市名称',
  `city_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市代号',
  `area_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区名称',
  `area_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区代号',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member
-- ----------------------------

-- ----------------------------
-- Table structure for member_account
-- ----------------------------
DROP TABLE IF EXISTS `member_account`;
CREATE TABLE `member_account`  (
  `id` bigint NOT NULL COMMENT '唯一主键',
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员id',
  `balance` decimal(10, 2) NULL DEFAULT NULL COMMENT '余额',
  `frozen` decimal(10, 2) NULL DEFAULT NULL COMMENT '冻结金额',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员账户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_account
-- ----------------------------
INSERT INTO `member_account` VALUES (1610455607277760514, 1483383250707529730, 1.00, 3.00, NULL);
INSERT INTO `member_account` VALUES (1610455607869157378, 1601103918091395073, 2.00, 2.00, NULL);
INSERT INTO `member_account` VALUES (1610455608426999810, 1601104141689741314, 3.00, 1.00, NULL);
INSERT INTO `member_account` VALUES (1611263562248908801, 1611263561389076481, 0.00, 0.00, NULL);

-- ----------------------------
-- Table structure for member_account_serial
-- ----------------------------
DROP TABLE IF EXISTS `member_account_serial`;
CREATE TABLE `member_account_serial`  (
  `id` bigint NOT NULL COMMENT '唯一主键',
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员id',
  `member_account_id` bigint NULL DEFAULT NULL COMMENT '会员余额账户id',
  `money` decimal(10, 2) NULL DEFAULT NULL COMMENT '金额',
  `type` tinyint NULL DEFAULT NULL COMMENT '流水类型（加或减）',
  `category` tinyint NULL DEFAULT NULL COMMENT '类别（为什么加或减）',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员账户流水' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_account_serial
-- ----------------------------

-- ----------------------------
-- Table structure for member_address
-- ----------------------------
DROP TABLE IF EXISTS `member_address`;
CREATE TABLE `member_address`  (
  `id` bigint NOT NULL COMMENT '主键',
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员ID',
  `consignee` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '收货人',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `province` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `province_code` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省地区编号形式',
  `city` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `city_code` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市地区编号形式',
  `area` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `area_code` varchar(120) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区地区编号形式',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '详细地址',
  `is_default` tinyint(1) NULL DEFAULT NULL COMMENT '是否为默认地址1.是2.否',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NULL DEFAULT 0 COMMENT '状态',
  `is_deleted` int NULL DEFAULT 2 COMMENT '删除状态 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员收货地址表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_address
-- ----------------------------
INSERT INTO `member_address` VALUES (1595243711633281025, 1468134832232628225, 'Wxx', '15617788888', '北京市', '11', '市辖区', '1101', '东城区', '110101', '详细地址捏222', 1, NULL, '2022-11-23 10:31:54', NULL, NULL, 0, 1);
INSERT INTO `member_address` VALUES (1595252909154738178, 1468134832232628225, 'Wxxz', '15617788888', '天津市', '12', '市辖区', '1201', '和平区', '120101', 'x详细地址捏112', 1, NULL, '2022-11-23 11:08:27', NULL, NULL, 0, 1);
INSERT INTO `member_address` VALUES (1595253035520729089, 1468134832232628225, 'wzzzz', '15555555555', '山西省', '14', '太原市', '1401', '小店区', '140105', '惺惺惜惺惺', 2, NULL, '2022-11-23 11:08:57', NULL, NULL, 0, 1);
INSERT INTO `member_address` VALUES (1595254460069937153, 1468134832232628225, '新增测试', '15555555555', '上海市', '31', '市辖区', '3101', '黄浦区', '310101', '啊啊啊啊啊啊', 2, NULL, '2022-11-23 11:14:36', NULL, NULL, 0, 1);
INSERT INTO `member_address` VALUES (1597038518215933954, 2, '1', '3', '', NULL, '', NULL, '', NULL, '', NULL, -1, '2022-11-28 09:23:49', NULL, NULL, 0, 1);
INSERT INTO `member_address` VALUES (1597081384661336065, NULL, '12', '2', '3', NULL, '4', NULL, '', NULL, '', NULL, -1, '2022-11-28 12:14:09', -1, '2022-12-09 10:34:04', 0, 1);
INSERT INTO `member_address` VALUES (1600789790193856513, 1483383250707529730, '1', '15555555555', '北京市', '110000', '北京市', '110100', '东城区', '110101', '111111', 2, -1, '2022-12-08 17:50:02', NULL, NULL, 0, 2);
INSERT INTO `member_address` VALUES (1601043048540069889, 1600770114457919489, '浪漫经典', '13455656999', '北京市', '110000', '北京市', '110100', '朝阳区', '110105', '柳荫街1号', 1, -1, '2022-12-09 10:36:23', NULL, NULL, 0, 2);
INSERT INTO `member_address` VALUES (1601150020077694977, 1601104141689741314, '浪漫经典', '13455656999', '北京市', '110000', '北京市', '110100', '东城区', '110101', '柳荫街1号', 1, -1, '2022-12-09 17:41:27', NULL, NULL, 0, 2);

-- ----------------------------
-- Table structure for member_bank_card
-- ----------------------------
DROP TABLE IF EXISTS `member_bank_card`;
CREATE TABLE `member_bank_card`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员ID',
  `card_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡号',
  `real_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `id_card_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `open_account` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '开户行名称(分行)',
  `bank_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '所属银行',
  `phone` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行预留手机号',
  `card_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行卡照片地址',
  `valid_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡片有效期',
  `card_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡类型DC(借记卡),  CC(贷记卡),  SCC(准贷记卡), DCC(存贷合一卡), PC(预付卡)',
  `bank_type` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '银行类型',
  `city` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支行所在省市',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NULL DEFAULT 0 COMMENT '状态',
  `is_deleted` int NULL DEFAULT 2 COMMENT '删除状态 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员银行卡表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_bank_card
-- ----------------------------
INSERT INTO `member_bank_card` VALUES (1484371747803103233, 1483383250707529730, '6222021607023310981', '孙余伟', '123', '123', '123', '15966198621', 'https://oss.icuapi.com/testtemp/bank_card.jpg', NULL, NULL, NULL, '北京市北京市', NULL, '2022-01-21 11:46:18', NULL, NULL, 0, 2);
INSERT INTO `member_bank_card` VALUES (1484720277638025218, 1483383250707529730, '6223910701516223', '马俊杰', '37070519961125331X', '某支行', '潍坊银行', '15966198621', '', NULL, NULL, NULL, '山东省潍坊市', NULL, '2022-01-22 10:51:14', -1, '2022-12-08 17:32:25', 0, 2);
INSERT INTO `member_bank_card` VALUES (1601052792181526530, 1600770114457919489, '6222521055538429', 'e', NULL, '交通', '交通银行', '15684314381', '', NULL, '2', '', '', -1, '2022-12-09 11:15:06', NULL, NULL, 0, 2);
INSERT INTO `member_bank_card` VALUES (1601053073829040129, 1600770114457919489, '6212261607002042373', NULL, NULL, '东关支行', '中国工商银行', '15684314381', '', NULL, '1', '123', '123', -1, '2022-12-09 11:16:14', NULL, NULL, 0, 2);
INSERT INTO `member_bank_card` VALUES (1601094948396150786, 1483383250707529730, '1', '', NULL, '', '', '', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221209/1670565755300.webp', NULL, '', '', '', -1, '2022-12-09 14:02:37', NULL, NULL, 0, 2);
INSERT INTO `member_bank_card` VALUES (1601149863139422210, 1601104141689741314, '6222021607023310981', '孙余伟', NULL, '河东支行', '中国工商银行', '13455656999', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221209/1670578842927.webp', '09/30', '1', '', '山东省潍坊市', -1, '2022-12-09 17:40:50', NULL, NULL, 0, 2);

-- ----------------------------
-- Table structure for member_extend_field
-- ----------------------------
DROP TABLE IF EXISTS `member_extend_field`;
CREATE TABLE `member_extend_field`  (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `member_id` bigint UNSIGNED NULL DEFAULT NULL COMMENT '关联会员',
  `signature` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '个性签名',
  `cardno` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '会员卡',
  `source` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '来源',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `idcard` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号码',
  `card_num` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '卡号',
  `jiguan` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '籍贯',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员扩展字段' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_extend_field
-- ----------------------------
INSERT INTO `member_extend_field` VALUES (3, 1600770114457919489, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `member_extend_field` VALUES (4, 1483383250707529730, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `member_extend_field` VALUES (5, 1601104141689741314, '个性签名可以随便填写一点内容！', '4444444', '3', '孙余伟', '222403199006285911', '1223123123', '山东');
INSERT INTO `member_extend_field` VALUES (6, 1601103918091395073, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `member_extend_field` VALUES (7, 1468134832232628225, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `member_extend_field` VALUES (8, 1611263561389076481, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for member_extend_field_config
-- ----------------------------
DROP TABLE IF EXISTS `member_extend_field_config`;
CREATE TABLE `member_extend_field_config`  (
  `id` bigint NOT NULL COMMENT '主键',
  `col_title` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段标题',
  `col_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段名',
  `prop_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '属性名',
  `col_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字段类型',
  `form_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '表单类型',
  `sort` int UNSIGNED NULL DEFAULT 1 COMMENT '排序',
  `data_type` int NULL DEFAULT NULL COMMENT '数据集类型',
  `data_config` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '数据集配置',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '用户扩展字段配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_extend_field_config
-- ----------------------------
INSERT INTO `member_extend_field_config` VALUES (1600774965661220866, '个性签名', 'signature', 'signature', 'text', '2', 20, 2, '{\"dict\":\"sex\",\"enumObj\":{\"enumName\":\"enumName\",\"moduleName\":\"moduleName\"},\"urlObj\":{\"url\":\"\",\"code\":\"\",\"name\":\"\"},\"jsonObj\":\"\"}');
INSERT INTO `member_extend_field_config` VALUES (1601467220311351298, '会员卡', 'cardno', 'cardno', 'varchar(255)', '1', 3, NULL, '{\"dict\":\"\",\"enumObj\":{\"enumName\":\"\",\"moduleName\":\"\"},\"urlObj\":{\"url\":\"\",\"code\":\"\",\"name\":\"\"},\"jsonObj\":\"\"}');
INSERT INTO `member_extend_field_config` VALUES (1601467326276247553, '来源', 'source', 'source', 'varchar(255)', '4', 1, 1, '{\"dict\":\"source\",\"enumObj\":{\"enumName\":\"\",\"moduleName\":\"\"},\"urlObj\":{\"url\":\"\",\"code\":\"\",\"name\":\"\"},\"jsonObj\":\"\"}');
INSERT INTO `member_extend_field_config` VALUES (1601476162366152705, '真实姓名', 'name', 'name', 'varchar(255)', '1', 99, NULL, '{\"dict\":\"\",\"enumObj\":{\"enumName\":\"\",\"moduleName\":\"\"},\"urlObj\":{\"url\":\"\",\"code\":\"\",\"name\":\"\"},\"jsonObj\":\"\"}');
INSERT INTO `member_extend_field_config` VALUES (1601476416780050434, '身份证号码', 'idcard', 'idcard', 'varchar(255)', '1', 98, NULL, '{\"dict\":\"\",\"enumObj\":{\"enumName\":\"\",\"moduleName\":\"\"},\"urlObj\":{\"url\":\"\",\"code\":\"\",\"name\":\"\"},\"jsonObj\":\"\"}');
INSERT INTO `member_extend_field_config` VALUES (1610101893061713922, '卡号', 'card_num', 'cardNum', 'varchar(255)', '1', 1, NULL, '{\"dict\":\"\",\"enumObj\":{\"enumName\":\"\",\"moduleName\":\"\"},\"urlObj\":{\"url\":\"\",\"code\":\"\",\"name\":\"\"},\"jsonObj\":\"\"}');
INSERT INTO `member_extend_field_config` VALUES (1611244178734346242, '籍贯', 'jiguan', 'jiguan', 'varchar(255)', '1', 1, NULL, '{\"dict\":\"\",\"enumObj\":{\"enumName\":\"\",\"moduleName\":\"\"},\"urlObj\":{\"url\":\"\",\"code\":\"\",\"name\":\"\"},\"jsonObj\":\"\"}');

-- ----------------------------
-- Table structure for member_level
-- ----------------------------
DROP TABLE IF EXISTS `member_level`;
CREATE TABLE `member_level`  (
  `id` bigint NOT NULL COMMENT '唯一主键',
  `level` int NULL DEFAULT NULL COMMENT '级别',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '名称',
  `up_conditions` int NULL DEFAULT NULL COMMENT '升级条件（三种）',
  `order_total` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单总额',
  `order_count` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单次数',
  `goods_id` bigint NULL DEFAULT NULL COMMENT '指定商品id',
  `discount` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '折扣',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NULL DEFAULT 0 COMMENT '状态',
  `is_deleted` int NULL DEFAULT 2 COMMENT '删除状态 YesNoEnum。 yes删除；no未删除',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员等级表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_level
-- ----------------------------
INSERT INTO `member_level` VALUES (1608359979341045761, 1, '随便添加一个等级', 1, '100', '0', NULL, NULL, -1, '2022-12-29 15:11:16', NULL, NULL, 0, 2);
INSERT INTO `member_level` VALUES (1610104002825986050, 1, '白银会员', 2, '100', '200', NULL, NULL, -1, '2023-01-03 10:41:23', -1, '2023-01-03 14:04:52', 0, 2);
INSERT INTO `member_level` VALUES (1610164882514358273, 1, '测试商品', 3, '', '', 1465942819803672577, NULL, -1, '2023-01-03 14:43:18', -1, '2023-01-03 14:43:35', 0, 2);

-- ----------------------------
-- Table structure for member_login_log
-- ----------------------------
DROP TABLE IF EXISTS `member_login_log`;
CREATE TABLE `member_login_log`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员id',
  `invoke_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用路径',
  `remote_addr` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用ip',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登录地址',
  `cus_browser` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  `cus_os` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `invoke_params` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '调用参数',
  `invoke_status` varchar(2) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '调用状态 00 成功 01 失败',
  `invoke_time` datetime NULL DEFAULT NULL COMMENT '调用时间',
  `result_data` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '响应结果',
  `elapsed_time` bigint NULL DEFAULT NULL COMMENT '耗时(毫秒)',
  `create_time` datetime NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
  `login_method` int NULL DEFAULT NULL COMMENT '登录方式(验证码/账号密码/微信/小程序等等)',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员登录日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_login_log
-- ----------------------------

-- ----------------------------
-- Table structure for member_points
-- ----------------------------
DROP TABLE IF EXISTS `member_points`;
CREATE TABLE `member_points`  (
  `id` bigint NOT NULL COMMENT '唯一主键',
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员id',
  `balance` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '剩余积分',
  `frozen` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '冻结积分',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员积分表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_points
-- ----------------------------
INSERT INTO `member_points` VALUES (1601104141689755314, 1601104141689741314, '10', '3', 2);
INSERT INTO `member_points` VALUES (1610455607592333313, 1483383250707529730, '2', '2', NULL);
INSERT INTO `member_points` VALUES (1610455608145981441, 1601103918091395073, '3', '1', NULL);
INSERT INTO `member_points` VALUES (1611263562701893634, 1611263561389076481, '4', '0', NULL);

-- ----------------------------
-- Table structure for member_points_serial
-- ----------------------------
DROP TABLE IF EXISTS `member_points_serial`;
CREATE TABLE `member_points_serial`  (
  `id` bigint NOT NULL COMMENT '唯一主键',
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员id',
  `member_points_id` bigint NULL DEFAULT NULL COMMENT '会员积分账户id',
  `points` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变动积分',
  `account_points` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '变动后账户积分 实时积分',
  `type` tinyint NULL DEFAULT NULL COMMENT '流水类型（加或减）',
  `category` tinyint NULL DEFAULT NULL COMMENT '类别（为什么加或减）',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单ID(购物送积分/关联订单/)',
  `memo` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员积分流水' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_points_serial
-- ----------------------------
INSERT INTO `member_points_serial` VALUES (1606188473563467778, 1601104141689741314, 1601104141689755314, '200.66', NULL, 2, 2, NULL, '管理员增加 这是备注信息', NULL, '2022-12-23 15:22:28', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1606195596108226561, 1601104141689741314, 1601104141689755314, '134.66', NULL, 3, 2, NULL, '管理员增加 这是扣减备注信息', NULL, '2022-12-23 15:50:46', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1606204278313201666, 1601104141689741314, 1601104141689755314, '134.66', NULL, 1, 2, NULL, '管理员增加 这是扣减备注信息', NULL, '2022-12-23 16:25:16', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1606204445737234433, 1601104141689741314, 1601104141689755314, '134.66', NULL, 1, 2, NULL, '管理员增加 这是扣减备注信息', NULL, '2022-12-23 16:25:56', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1606205244517355522, 1601104141689741314, 1601104141689755314, '300', NULL, 1, 2, NULL, '管理员增加 这是扣减备注信息', NULL, '2022-12-23 16:29:07', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610195138000449538, 1601104141689741314, 1601104141689755314, '1', NULL, 1, 2, NULL, '管理员增加 22', -1, '2023-01-03 16:43:32', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610473596362403842, 1601104141689741314, 1601104141689755314, '2', NULL, 1, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:10:01', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610473675097878530, 1601104141689741314, 1601104141689755314, '33', NULL, 2, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:10:20', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610474387794014209, 1601104141689741314, 1601104141689755314, '22', NULL, 1, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:13:10', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610474654807601154, 1601104141689741314, 1601104141689755314, '22', NULL, 1, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:14:14', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610474756511084546, 1601104141689741314, 1601104141689755314, '33', NULL, 1, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:14:38', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610475347899559938, 1601104141689741314, 1601104141689755314, '1', NULL, 2, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:16:59', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610475370326503426, 1601104141689741314, 1601104141689755314, '33', NULL, 3, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:17:04', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610479190305644546, 1601104141689741314, 1601104141689755314, '3', NULL, 1, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:32:15', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610479285478596610, 1601104141689741314, 1601104141689755314, '-1', NULL, 1, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:32:38', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610480765015109634, 1601104141689741314, 1601104141689755314, '2', NULL, 2, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:38:30', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610480889036484609, 1601104141689741314, 1601104141689755314, '-1', NULL, 1, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:39:00', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610481566433386497, 1601104141689741314, 1601104141689755314, '1', NULL, 1, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:41:41', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610481584938655746, 1601104141689741314, 1601104141689755314, '-1', NULL, 1, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:41:46', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610481775708184578, 1601104141689741314, 1601104141689755314, '2', NULL, 1, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:42:31', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610482361493024769, 1601104141689741314, 1601104141689755314, '-5', NULL, 2, 2, NULL, '管理员增加 ', -1, '2023-01-04 11:44:51', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610560141719212034, 1601104141689741314, 1601104141689755314, '10', NULL, 2, 2, NULL, '管理员增加 ', -1, '2023-01-04 16:53:55', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610562060844003329, 1601104141689741314, 1601104141689755314, '3', NULL, 2, 2, NULL, '管理员增加 这是备注', -1, '2023-01-04 17:01:33', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610562161993838593, 1601104141689741314, 1601104141689755314, '10', NULL, 3, 2, NULL, '管理员增加 这是减少的备注', -1, '2023-01-04 17:01:57', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610566126600851457, 1601104141689741314, 1601104141689755314, '10', NULL, 2, 2, NULL, '管理员增加 ', -1, '2023-01-04 17:17:42', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610566171198885889, 1601104141689741314, 1601104141689755314, '5', NULL, 3, 2, NULL, '管理员增加 ', -1, '2023-01-04 17:17:53', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1610822872963715073, 1601104141689741314, 1601104141689755314, '5', '10', 2, 2, NULL, '管理员增加 ', -1, '2023-01-05 10:17:55', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1628279858505240577, 1611263561389076481, 1611263562701893634, '2', '2', 2, 2, NULL, '管理员增加 ', -1, '2023-02-22 14:25:45', NULL, NULL, 2, NULL);
INSERT INTO `member_points_serial` VALUES (1628280105692352513, 1611263561389076481, 1611263562701893634, '2', '4', 2, 2, NULL, '管理员增加 ', -1, '2023-02-22 14:26:44', NULL, NULL, 2, NULL);

-- ----------------------------
-- Table structure for member_realinfo
-- ----------------------------
DROP TABLE IF EXISTS `member_realinfo`;
CREATE TABLE `member_realinfo`  (
  `id` bigint NOT NULL COMMENT '唯一主键',
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员id',
  `real_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '真实姓名',
  `card_num` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证号',
  `img_front` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证正面照片',
  `img_back` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '身份证背面照片',
  `birthday` date NULL DEFAULT NULL COMMENT '出生日期',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '住址',
  `validity_date` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证件有效期',
  `issuing_authority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '发证机关',
  `nation` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '民族',
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '性别',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建者',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '更新者',
  `update_time` datetime NULL DEFAULT NULL COMMENT '更新时间',
  `status` int NULL DEFAULT 0 COMMENT '状态',
  `is_deleted` int NULL DEFAULT 2 COMMENT '删除状态 YesNoEnum。 yes删除；no未删除',
  `card_type` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业执照类型',
  `person` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '法定代表人',
  `capital` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '注册资本',
  `business` mediumtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '经营范围',
  `enterprise_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公司名称',
  `credit_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '统一社会信用代码',
  `reg_date` date NULL DEFAULT NULL COMMENT '成立日期',
  `business_term` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '营业期限',
  `residence` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '住所',
  `reg_authority` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '登记机关',
  `award_date` date NULL DEFAULT NULL COMMENT '发证日期',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员真实信息表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_realinfo
-- ----------------------------
INSERT INTO `member_realinfo` VALUES (1485532369550999554, 1483383250707529730, '马俊杰', '37070519961125331X', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20220123/1642928702424.jpg', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20220124/1642987667194.jpg', '1996-11-25', '山东省潍坊市奎文区南苑街道茂子庄村236号', '2013.07.15-2023.07.15', '潍坊市公安局奎文分局', '汉', '男', NULL, '2022-01-24 16:38:11', NULL, NULL, 0, 2, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);

-- ----------------------------
-- Table structure for member_tag
-- ----------------------------
DROP TABLE IF EXISTS `member_tag`;
CREATE TABLE `member_tag`  (
  `id` bigint NOT NULL COMMENT '唯一主键',
  `name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '标签名',
  `sort` int UNSIGNED NULL DEFAULT NULL COMMENT '排序字段',
  `status` tinyint NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '会员标签表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_tag
-- ----------------------------
INSERT INTO `member_tag` VALUES (1601506748346343425, '测测测测', NULL, NULL, -1, '2022-12-10 17:18:58', NULL, NULL, 2);
INSERT INTO `member_tag` VALUES (1601506774778847233, '测测测测2', NULL, NULL, -1, '2022-12-10 17:19:04', NULL, NULL, 2);
INSERT INTO `member_tag` VALUES (1601506803144925186, '测测测测测3', NULL, NULL, -1, '2022-12-10 17:19:11', NULL, NULL, 2);
INSERT INTO `member_tag` VALUES (1610104860682788865, '123123', NULL, NULL, -1, '2023-01-03 10:44:48', NULL, NULL, 2);
INSERT INTO `member_tag` VALUES (1610104860879921153, '有车有房', NULL, NULL, -1, '2023-01-03 10:44:48', NULL, NULL, 2);
INSERT INTO `member_tag` VALUES (1610104861077053441, '有钱', 2, NULL, -1, '2023-01-03 10:44:48', -1, '2023-01-04 16:17:11', 2);
INSERT INTO `member_tag` VALUES (1610104861274185730, '无奈', 2, NULL, -1, '2023-01-03 10:44:48', -1, '2023-01-04 16:17:11', 2);
INSERT INTO `member_tag` VALUES (1610105040849117185, '有车有房2', 1, NULL, -1, '2023-01-03 10:45:31', -1, '2023-01-04 16:17:23', 2);
INSERT INTO `member_tag` VALUES (1610514107206983681, 'asdasd', NULL, NULL, -1, '2023-01-04 13:51:00', NULL, NULL, 2);
INSERT INTO `member_tag` VALUES (1610531802333822978, 'asdasd', NULL, NULL, -1, '2023-01-04 15:01:19', NULL, NULL, 2);
INSERT INTO `member_tag` VALUES (1610534904134889474, 'asdasd', NULL, NULL, -1, '2023-01-04 15:13:38', NULL, NULL, 2);
INSERT INTO `member_tag` VALUES (1610549369408540674, 'cecececc', NULL, NULL, -1, '2023-01-04 16:11:07', NULL, NULL, 2);
INSERT INTO `member_tag` VALUES (1610550751968591873, 'cecececc', 1, NULL, -1, '2023-01-04 16:16:37', -1, '2023-01-04 16:16:36', 2);

-- ----------------------------
-- Table structure for member_wx
-- ----------------------------
DROP TABLE IF EXISTS `member_wx`;
CREATE TABLE `member_wx`  (
  `id` bigint NOT NULL COMMENT '唯一主键',
  `member_id` bigint NULL DEFAULT NULL COMMENT '关联主表ID',
  `nick_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '用户昵称',
  `open_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信openid',
  `union_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '微信unionid',
  `sex` int NULL DEFAULT NULL COMMENT '性别 1为男性，2为女性',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `country` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '国家',
  `head_img_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '头像',
  `status` tinyint NULL DEFAULT NULL COMMENT '会员状态',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信会员表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of member_wx
-- ----------------------------
INSERT INTO `member_wx` VALUES (1601103876894941185, 1601103918091395073, '他的国', 'oUVIv4zI81c9F4iu5osJcMsEgsg4', 'outmJw2_zE6LeECNbWK06ZCMdgA0', NULL, NULL, NULL, NULL, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221209/1670567874082.webp', NULL, NULL, '2022-12-09 14:38:06', NULL, NULL, 2);
INSERT INTO `member_wx` VALUES (1601104120957296641, 1601104141689741314, '浪漫经典', 'oUVIv4wAPWIK4zDGjdGDCdx4zIO4', 'outmJw7RyNDJjWedoyJzVsoUIFdA', NULL, NULL, NULL, NULL, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20221209/1670567941500.webp', NULL, NULL, '2022-12-09 14:39:04', NULL, NULL, 2);
INSERT INTO `member_wx` VALUES (1611270102624313346, NULL, '1', 'oUVIv49MlPYqhlOr-lRA2MTLxlOc', 'outmJw0W6xQ6TWgUB7pmEqwlPrys', NULL, NULL, NULL, NULL, 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20230106/1672991699804.webp', NULL, NULL, '2023-01-06 15:55:03', NULL, NULL, 2);

-- ----------------------------
-- Table structure for mytable
-- ----------------------------
DROP TABLE IF EXISTS `mytable`;
CREATE TABLE `mytable`  (
  `id` int UNSIGNED NOT NULL AUTO_INCREMENT,
  `title` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  FULLTEXT INDEX `content`(`content`)
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of mytable
-- ----------------------------
INSERT INTO `mytable` VALUES (2, '我是测试内容keyword', '我是测试内容keyword');

-- ----------------------------
-- Table structure for pay_ali_config
-- ----------------------------
DROP TABLE IF EXISTS `pay_ali_config`;
CREATE TABLE `pay_ali_config`  (
  `id` bigint NOT NULL COMMENT '主键',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'appId',
  `private_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '私钥',
  `ali_pay_public_key` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '公钥',
  `notify_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付完成后的异步通知地址',
  `return_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付完成后的同步返回地址',
  `pay_type_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付类别',
  `scene_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场景编码',
  `status` int NULL DEFAULT NULL COMMENT '1 启用 2 禁用 3 不可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付宝支付配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pay_ali_config
-- ----------------------------

-- ----------------------------
-- Table structure for pay_scene
-- ----------------------------
DROP TABLE IF EXISTS `pay_scene`;
CREATE TABLE `pay_scene`  (
  `id` bigint NOT NULL COMMENT '主键',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场景编码',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场景名称',
  `remark` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '支付场景表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pay_scene
-- ----------------------------
INSERT INTO `pay_scene` VALUES (1544929974834466817, '001', '公众号商城', '');
INSERT INTO `pay_scene` VALUES (1551479814854930434, '002', '微信小程序', '');
INSERT INTO `pay_scene` VALUES (1555022515839754242, '003', '抖音小程序', '');
INSERT INTO `pay_scene` VALUES (1555023194377478146, 'comm_pay', '公共', '');

-- ----------------------------
-- Table structure for pay_wx_config
-- ----------------------------
DROP TABLE IF EXISTS `pay_wx_config`;
CREATE TABLE `pay_wx_config`  (
  `id` bigint NOT NULL COMMENT '主键',
  `app_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公众号/AppappId',
  `app_secret` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '公众号appSecret',
  `mch_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户号',
  `mch_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户V2秘钥',
  `mch_serial_no` varchar(512) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '证书序列号',
  `api_v3_key` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'V3密钥',
  `key_path` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '商户api私钥证书路径',
  `notify_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付完成后的异步通知地址',
  `return_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付完成后的同步返回地址',
  `pay_type_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '支付类别',
  `scene_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '场景编码',
  `status` int NULL DEFAULT NULL COMMENT '1 启用 2 禁用 3 不可用',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '微信支付配置表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of pay_wx_config
-- ----------------------------

-- ----------------------------
-- Table structure for sale_coupon
-- ----------------------------
DROP TABLE IF EXISTS `sale_coupon`;
CREATE TABLE `sale_coupon`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `coupon_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '优惠券名称',
  `get_type` int NULL DEFAULT NULL COMMENT '领取中心显示 0 不 1 显示',
  `get_max` int NULL DEFAULT NULL COMMENT '每人限领张数',
  `get_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '领取卡券链接',
  `use_type` int NULL DEFAULT NULL COMMENT '消费方式 0 付款使用 1 下单使用',
  `return_type` int NULL DEFAULT NULL COMMENT '退回方式 0 不可退回 1 取消订单(未付款) 2.退款可以退回',
  `enough` decimal(32, 2) NULL DEFAULT NULL COMMENT '消费满多少可用 0 不限制',
  `time_limit` int NULL DEFAULT NULL COMMENT '0 领取后几天有效 1 时间范围',
  `time_days` int NULL DEFAULT NULL COMMENT '获得后可使用 0 不限时间 >0 天',
  `time_start` datetime NULL DEFAULT NULL COMMENT '开始时间',
  `time_end` datetime NULL DEFAULT NULL COMMENT '结束时间',
  `back_type` int NULL DEFAULT NULL COMMENT '返利方式 0 立减 1 打折',
  `deduct` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '立减',
  `discount` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '折扣',
  `desc` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '介绍',
  `total` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '总发放数量 -1 不限制',
  `money` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '购买价格',
  `credit` decimal(32, 2) NULL DEFAULT NULL COMMENT '消耗积分',
  `display_order` int NULL DEFAULT NULL COMMENT '	排序',
  `limit_good_type` int NULL DEFAULT NULL COMMENT '是否限制商品 0 不限制 1 限制可以使用券  2限制不可以使用券',
  `limit_good_cate_type` int NULL DEFAULT NULL COMMENT '是否限制商品分类 0 不限制 1 限制可以使用券  2限制不可以使用券',
  `limit_good_cate_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '限制商品分类ID',
  `limit_good_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '限制商品ID',
  `is_limit_level` int NULL DEFAULT NULL COMMENT '是否限制会员级别 ',
  `limit_level_ids` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '会员级别ID 只有选中的级别能领取',
  `status` int NULL DEFAULT NULL COMMENT '状态YesNoEnum',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sale_coupon
-- ----------------------------
INSERT INTO `sale_coupon` VALUES (1611178610051465218, '撒大苏打实打实飒飒', 1, 2, 'sadasdasdasasasasd', 1, 1, 0.00, 1, 0, '2023-01-06 09:48:15', '2023-01-06 09:48:16', 1, '10', '10', '<p>asdasdadasdas</p>\n', '100', '10', 10.00, 0, 1, 1, 'asdasdasadss', 'asdasdasdasdas', 1, '1610164882514358273', 1, -1, '2023-01-06 09:51:30', NULL, NULL, 2);
INSERT INTO `sale_coupon` VALUES (1612289755894247425, '测试链接生成', 1, 1, 'www.zzz.com?id=1612289755894247425', 1, 1, 1.00, NULL, 1, '2023-01-09 11:26:22', '2023-01-09 11:26:21', 1, '10', '10', '<p>阿三大苏打飒飒</p>\n', '2', '1', 1.00, 0, 1, 1, '', '', 2, '', 1, -1, '2023-01-09 11:26:47', -1, '2023-01-09 11:26:47', 2);
INSERT INTO `sale_coupon` VALUES (1612342607215792129, '测试', 1, 1, 'www.zzz.com?id=1612342607215792129', 1, 1, 1.00, NULL, 1, '2023-01-09 14:56:13', '2023-01-09 14:56:14', 1, '10', '10', '<p>阿斯顿打撒打撒打撒</p>\n', '1', '1', 1.00, 0, 1, 1, '', '', 1, '1610164882514358273', 1, -1, '2023-01-09 14:56:48', -1, '2023-01-09 14:56:48', 2);
INSERT INTO `sale_coupon` VALUES (1614902299503681537, '九折券', 1, 2, 'www.zzz.com?id=1614902299503681537', 2, 1, 10000.00, 1, 7, NULL, NULL, 2, '', '0.9', '', '100', '0', 0.00, 1, 1, 1, '', '', 2, '', 1, -1, '2023-01-16 16:28:06', -1, '2023-01-16 16:28:06', 2);

-- ----------------------------
-- Table structure for sale_coupon_data
-- ----------------------------
DROP TABLE IF EXISTS `sale_coupon_data`;
CREATE TABLE `sale_coupon_data`  (
  `id` bigint NOT NULL COMMENT '主键id',
  `member_id` bigint NULL DEFAULT NULL COMMENT '会员id',
  `coupon_id` bigint NULL DEFAULT NULL COMMENT '优惠券ID',
  `get_type` int NULL DEFAULT NULL COMMENT '获得方式 \r\n0 后台发放 1 领券中心 2积分商城 3 兑换中心',
  `use_time` datetime NULL DEFAULT NULL COMMENT '使用时间',
  `get_time` datetime NULL DEFAULT NULL COMMENT '获取时间',
  `order_sn` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '订单编号',
  `effective_time` datetime NULL DEFAULT NULL COMMENT '生效时间(什么时候生效)',
  `overdue_time` datetime NULL DEFAULT NULL COMMENT '到期时间',
  `status` int NULL DEFAULT NULL COMMENT '状态\r\n未使用\r\n已使用\r\n已过期\r\n已失效(作废 作废时只把未使用的作废)',
  `pay_type` int NULL DEFAULT NULL COMMENT '支付类型  -1 免费领取 1积分支付',
  `pay_number` int NULL DEFAULT NULL COMMENT '支付余额数量(弃用)',
  `point_number` int NULL DEFAULT NULL COMMENT '支付积分数量',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '优惠券领取记录' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of sale_coupon_data
-- ----------------------------
INSERT INTO `sale_coupon_data` VALUES (1, 1611263561389076481, 1614902299503681537, 2, NULL, '2023-01-16 16:28:34', NULL, '2023-01-23 16:28:36', '2023-01-23 16:28:42', 1, 1, 0, 0, NULL, '2023-01-16 16:28:51', NULL, NULL, 2);
INSERT INTO `sale_coupon_data` VALUES (1612381531560718337, 1601104141689741314, 1612289755894247425, 2, NULL, '2023-01-09 17:31:28', NULL, '2023-01-09 17:31:28', '2023-01-09 17:31:28', 1, 1, 0, 0, NULL, '2023-01-09 17:31:28', NULL, NULL, 2);

-- ----------------------------
-- Table structure for tmp_demo
-- ----------------------------
DROP TABLE IF EXISTS `tmp_demo`;
CREATE TABLE `tmp_demo`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '输入框',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '富文本',
  `auth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '作者',
  `phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `province` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省',
  `city` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市',
  `area` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区',
  `province_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '省名',
  `city_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '市名',
  `area_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '区名称',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `images` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
  `files` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件',
  `video` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频',
  `longitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地图-经度',
  `latitude` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地图-纬度',
  `vue_number` int NULL DEFAULT NULL COMMENT '数字',
  `vue_radio` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单选',
  `vue_textarea` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '多行文本',
  `vue_checkbox` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复选',
  `vue_select` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下拉',
  `select_enum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '枚举下拉',
  `select_url` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url下拉',
  `select_json` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'json下拉',
  `vue_date` date NULL DEFAULT NULL COMMENT '日期',
  `vue_datetime` datetime NULL DEFAULT NULL COMMENT '日期时间',
  `map_data` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '地图相关信息',
  `industry_data` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '行业信息',
  `users_data` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '人员部门信息',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_post` bigint NULL DEFAULT NULL COMMENT '创建职位',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '组件示例表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tmp_demo
-- ----------------------------
INSERT INTO `tmp_demo` VALUES (1400345823066976258, '1', '&lt;p&gt;我爱北京天安门，天安门上太阳升&lt;/p&gt;\n', '3', '13200000001', NULL, NULL, NULL, NULL, NULL, NULL, '5@123.com', 'http://localhost/image/upload\\20210603\\1622703251217.jpg', '[{\"name\":\"1.png\",\"url\":\"http://localhost/image/upload\\\\20210603\\\\1622703253922.png\"},{\"name\":\"springboot参数.docx\",\"url\":\"http://localhost/image/upload\\\\20210603\\\\1622707328114.docx\"}]', 'http://localhost/image/upload\\20210603\\1622703245396.mp4', '6', '7', 8, 'dictDemo001', '9', 'dictDemo002', 'dictDemo001', '2', '1376744834481156097', '3', '2021-06-17', '2021-06-03 14:57:09', NULL, NULL, NULL, 10, 11, 11, 11, '2021-06-03 14:57:16', -1, '2021-06-03 15:22:03', 2);
INSERT INTO `tmp_demo` VALUES (1400358906036482050, '21', '<p>新华社北京7月31日电 中华人民共和国国防部7月31日在人民大会堂举行盛大招待会，热烈庆祝中国人民解放军建军95周年。中共中央总书记、国家主席、中央军委主席习近平和李克强、栗战书、汪洋、王沪宁、赵乐际、韩正、王岐山等党和国家领导人出席招待会。</p>\n', '22', '13200000000', '120000', '120100', '120102', '天津市', '天津市', '河东区', '7@126.com', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/fortress/20211108/1636342105298.png', '[{\"name\":\"1.png\",\"url\":\"http://localhost/image/upload\\\\20210611\\\\1623372412463.png\"},{\"name\":\"a189a09ef4e4aef014f7d027b3e2d8a7.jpg\",\"url\":\"http://localhost/image/upload\\\\20210611\\\\1623372578745.jpg\"}]', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20220301/1646118652556.mp4', '25', '26', -1, 'dictDemo002', '28', 'dictDemo001;dictDemo002', 'dictDemo002', '1', '1375356926163554305', '1', '2021-06-30', '2021-06-03 15:49:07', NULL, NULL, '{\"dept\":[\"1364860177091346434\",\"1418456998575386626\"],\"user\":[\"1554310592441548802\",\"1554310820401971202\"]}', 1, 222, 222, 222, '2021-06-03 15:49:15', -1, '2021-06-03 16:43:33', 2);

-- ----------------------------
-- Table structure for tmp_demo_generate
-- ----------------------------
DROP TABLE IF EXISTS `tmp_demo_generate`;
CREATE TABLE `tmp_demo_generate`  (
  `id` bigint NOT NULL COMMENT '主键ID',
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '中文',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '富文本',
  `auth` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '字母',
  `phone` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `images` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '图片',
  `files` text CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL COMMENT '文件',
  `video` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '视频',
  `vue_number` int NULL DEFAULT NULL COMMENT '数字',
  `vue_radio` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '单选',
  `vue_textarea` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '多行文本',
  `vue_checkbox` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '复选',
  `vue_select` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '下拉',
  `select_enum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '枚举下拉',
  `select_url` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url下拉',
  `select_json` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'json下拉',
  `vue_date` date NULL DEFAULT NULL COMMENT '日期',
  `vue_datetime` datetime NULL DEFAULT NULL COMMENT '日期时间',
  `status` int NULL DEFAULT NULL COMMENT '状态',
  `create_user` bigint NULL DEFAULT NULL COMMENT '创建人',
  `create_dept` bigint NULL DEFAULT NULL COMMENT '创建部门',
  `create_post` bigint NULL DEFAULT NULL COMMENT '创建职位',
  `create_time` datetime NULL DEFAULT NULL COMMENT '创建时间',
  `update_user` bigint NULL DEFAULT NULL COMMENT '修改人',
  `update_time` datetime NULL DEFAULT NULL COMMENT '修改时间',
  `is_deleted` int NULL DEFAULT NULL COMMENT '是否删除YesNoEnum',
  `redio_enum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '枚举单选',
  `radio_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url单选',
  `radio_json` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'json单选',
  `checkbox_enum` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '枚举复选',
  `checkbox_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'url复选',
  `checkbox_json` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT 'json复选',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '代码生成示例表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of tmp_demo_generate
-- ----------------------------
INSERT INTO `tmp_demo_generate` VALUES (1, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tmp_demo_generate` VALUES (2, '2', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tmp_demo_generate` VALUES (3, '3', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -1, '2021-09-06 10:37:24', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tmp_demo_generate` VALUES (4, '4', '<p>123</p>\n', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, -1, '2021-09-06 10:37:24', 2, NULL, NULL, NULL, '', '', '');
INSERT INTO `tmp_demo_generate` VALUES (1365259632462487554, 'upload\\20210305\\1614935904329.jpg', '<p>123456</p>', '水电费噶大商股份', NULL, NULL, NULL, NULL, NULL, NULL, '1', '收到嘎嘎收到噶事', '2;1', '1', NULL, NULL, NULL, '2021-02-23', '2021-03-31 08:00:00', NULL, -1, NULL, NULL, '2021-02-26 19:17:16', -1, '2021-03-10 16:22:20', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tmp_demo_generate` VALUES (1365259788901638146, '3', '3', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1, NULL, NULL, '2021-02-26 19:17:54', -1, '2021-03-03 12:19:26', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tmp_demo_generate` VALUES (1369564543350149122, 'upload\\20210310\\1615364588104.jpg', '<p>sgdsag</p>', '十多个傻', NULL, NULL, NULL, NULL, NULL, NULL, '1', '十多个傻', '1', '1', NULL, NULL, NULL, NULL, NULL, NULL, -1, NULL, NULL, '2021-03-10 16:23:27', -1, '2021-03-10 16:23:42', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tmp_demo_generate` VALUES (1369567800394969089, 'upload\\20210310\\1615365364539.jpg', '<p>地方嘎嘎十多个</p>', '十多个傻', NULL, NULL, NULL, NULL, NULL, NULL, '1', '十多个傻', '2;1', '1', NULL, NULL, NULL, '2021-03-29', '2021-03-31 00:02:00', NULL, -1, NULL, NULL, '2021-03-10 16:36:24', -1, '2021-03-10 16:36:36', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tmp_demo_generate` VALUES (1369580074992009217, 'upload\\20210310\\1615368290369.jpg', '<p>的感受到公司规定</p>', '十多个傻', NULL, NULL, NULL, NULL, NULL, NULL, '1', '十多个傻', '1;2', '2', NULL, NULL, NULL, '2021-03-29', '2021-03-31 02:00:00', NULL, -1, NULL, NULL, '2021-03-10 17:25:10', -1, '2021-06-09 14:08:22', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tmp_demo_generate` VALUES (1376733160042864642, 'mm001创建', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, 1375358377648590850, 1373902564618813441, 1374908419457572866, '2021-03-30 11:08:58', -1, '2021-06-09 14:08:20', 2, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO `tmp_demo_generate` VALUES (1376736233490423809, 'mm002创建', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '', '', NULL, NULL, NULL, NULL, NULL, NULL, 2, 1375358479276576770, 1373902564618813441, 1373916900020076545, '2021-03-30 11:21:11', -1, '2021-06-09 14:08:18', 2, '1', '1375356926163554305', '1', '1;2', '1375356926163554305;1375357147975127042', '1;2');
INSERT INTO `tmp_demo_generate` VALUES (1402519500831219714, '实体物品', '<p>我爱北京天安门啊，你好吗，我很好哦，今天天气有点阴哦1我爱北京天安门啊，你好吗，我很好哦，今天天气有点阴哦1我爱北京天安门啊，你好吗，我很好哦，今天天气有点阴哦1我爱北京天安门啊，你好吗，我很好哦，今天天气有点阴哦1我爱北京天安门啊，你好吗，我很好哦，今天天气有点阴哦1我爱北京天安门啊，你好吗，我很好哦，今天天气有点阴哦1我爱北京天安门啊，你好吗，我很好哦，今天天气有点阴哦1我爱北京天安门啊，你好吗，我很好哦，今天天气有点阴哦1</p><p>上来就店里看过见识到了个技术点，好的啊，是否解决了显示不稳定问题呢，看来是解决了</p><p><img class=\"image_resized\" style=\"width:300px;\" src=\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/fortress/20211018/1634547100127.jpg\"></p><p>&nbsp;</p><p><img class=\"image_resized\" style=\"width:300px;\" src=\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/fortress/20211019/1634625416267.png\" alt=\"\"></p><p>3</p><p>&nbsp;</p><p>&nbsp;4</p>', 'a', '13200000000', '5@12.com', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/fortress/20211018/1634545149845.png', '[{\"name\":\"证书使用说明.txt\",\"url\":\"https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/v2test/20220804/1659597665523.txt\"}]', 'https://hcses-1251334741.cos.ap-nanjing.myqcloud.com/fortress/20211018/1634547245779.mp4', 7, '1', '7', '1', '1', '1', '1375356926163554305', '1', '2021-06-30', '2021-06-09 14:54:30', 1, -1, NULL, NULL, '2021-06-09 14:54:41', -1, '2021-06-09 16:27:12', 2, '1', '1375356926163554305', '1', '1;2', '1375356926163554305;1375357147975127042;1376744834481156097;1376779233132007426', '1;2');

SET FOREIGN_KEY_CHECKS = 1;
