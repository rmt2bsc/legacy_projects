if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CASE__REF_40062_FH_CASE]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CASE_STATUS_HISTORY] DROP CONSTRAINT FK_FH_CASE__REF_40062_FH_CASE
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CASE__REF_10335_FH_CASE]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CASE_VITALS] DROP CONSTRAINT FK_FH_CASE__REF_10335_FH_CASE
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTR_REF_15327_FH_CASE]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTRACT] DROP CONSTRAINT FK_FH_CONTR_REF_15327_FH_CASE
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_D_REF_36740_FH_CASE]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_DATA] DROP CONSTRAINT FK_FH_LAB_D_REF_36740_FH_CASE
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CASE_REF_18618_FH_CASE_]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CASE] DROP CONSTRAINT FK_FH_CASE_REF_18618_FH_CASE_
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CASE_REF_15357_FH_CASE_]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CASE] DROP CONSTRAINT FK_FH_CASE_REF_15357_FH_CASE_
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CASE__REF_40065_FH_CASE_]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CASE_STATUS_HISTORY] DROP CONSTRAINT FK_FH_CASE__REF_40065_FH_CASE_
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTA_REF_32744_FH_CONTA]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTAIN] DROP CONSTRAINT FK_FH_CONTA_REF_32744_FH_CONTA
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTA_REF_32745_FH_CONTA]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTAIN] DROP CONSTRAINT FK_FH_CONTA_REF_32745_FH_CONTA
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTA_REF_32746_FH_CONTA]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTAIN] DROP CONSTRAINT FK_FH_CONTA_REF_32746_FH_CONTA
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTA_REF_32747_FH_CONTA]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTAIN] DROP CONSTRAINT FK_FH_CONTA_REF_32747_FH_CONTA
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTA_REF_32743_FH_CONTA]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTAIN_CODE] DROP CONSTRAINT FK_FH_CONTA_REF_32743_FH_CONTA
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTR_REF_15385_FH_CONTR]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTRACT_CHG_HISTORY] DROP CONSTRAINT FK_FH_CONTR_REF_15385_FH_CONTR
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTR_REF_10325_FH_CONTR]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTRACT_INS] DROP CONSTRAINT FK_FH_CONTR_REF_10325_FH_CONTR
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTR_REF_17489_FH_CONTR]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTRACT_ITEM] DROP CONSTRAINT FK_FH_CONTR_REF_17489_FH_CONTR
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERV__REF_45649_FH_CONTR]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERV_ORDER] DROP CONSTRAINT FK_FH_SERV__REF_45649_FH_CONTR
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTR_REF_15347_FH_CONTR]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTRACT] DROP CONSTRAINT FK_FH_CONTR_REF_15347_FH_CONTR
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_D_REF_36748_FH_LAB_C]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_DATA] DROP CONSTRAINT FK_FH_LAB_D_REF_36748_FH_LAB_C
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_D_REF_36751_FH_LAB_C]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_DATA] DROP CONSTRAINT FK_FH_LAB_D_REF_36751_FH_LAB_C
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_D_REF_36754_FH_LAB_C]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_DATA] DROP CONSTRAINT FK_FH_LAB_D_REF_36754_FH_LAB_C
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_D_REF_36760_FH_LAB_C]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_DATA] DROP CONSTRAINT FK_FH_LAB_D_REF_36760_FH_LAB_C
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_D_REF_36763_FH_LAB_C]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_DATA] DROP CONSTRAINT FK_FH_LAB_D_REF_36763_FH_LAB_C
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_I_REF_36766_FH_LAB_C]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_INJECTIONS] DROP CONSTRAINT FK_FH_LAB_I_REF_36766_FH_LAB_C
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_V_REF_40043_FH_LAB_C]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_VESSELS] DROP CONSTRAINT FK_FH_LAB_V_REF_40043_FH_LAB_C
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_C_REF_35352_FH_LAB_C]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_CODES] DROP CONSTRAINT FK_FH_LAB_C_REF_35352_FH_LAB_C
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_I_REF_36781_FH_LAB_D]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_INJECTIONS] DROP CONSTRAINT FK_FH_LAB_I_REF_36781_FH_LAB_D
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_LAB_V_REF_40038_FH_LAB_D]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_LAB_VESSELS] DROP CONSTRAINT FK_FH_LAB_V_REF_40038_FH_LAB_D
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_OBIT__REF_45575_FH_OBITU]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_OBIT_PRT_SRC] DROP CONSTRAINT FK_FH_OBIT__REF_45575_FH_OBITU
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_DISPO_REF_40094_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_DISPOSITION] DROP CONSTRAINT FK_FH_DISPO_REF_40094_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_OBITU_REF_9132_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_OBITUARY] DROP CONSTRAINT FK_FH_OBITU_REF_9132_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_PALLB_REF_30302_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_PALLBEARERS] DROP CONSTRAINT FK_FH_PALLB_REF_30302_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERV__REF_40132_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERV_ORDER] DROP CONSTRAINT FK_FH_SERV__REF_40132_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERVI_REF_9092_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERVICE_SCHEDULE] DROP CONSTRAINT FK_FH_SERVI_REF_9092_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERVI_REF_15397_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERVICE_SURVIVORS] DROP CONSTRAINT FK_FH_SERVI_REF_15397_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_MA_REF_31515_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_MASTER] DROP CONSTRAINT FK_FH_VA_MA_REF_31515_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CASE__REF_44264_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CASE_VITALS] DROP CONSTRAINT FK_FH_CASE__REF_44264_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTR_REF_35280_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTRACT_ITEM] DROP CONSTRAINT FK_FH_CONTR_REF_35280_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_DISPO_REF_40104_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_DISPOSITION] DROP CONSTRAINT FK_FH_DISPO_REF_40104_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_DISPO_REF_40109_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_DISPOSITION] DROP CONSTRAINT FK_FH_DISPO_REF_40109_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_OBIT__REF_45590_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_OBIT_PRT_SRC] DROP CONSTRAINT FK_FH_OBIT__REF_45590_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_OBIT__REF_45595_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_OBIT_PRT_SRC] DROP CONSTRAINT FK_FH_OBIT__REF_45595_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_PALLB_REF_30297_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_PALLBEARERS] DROP CONSTRAINT FK_FH_PALLB_REF_30297_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERV__REF_45543_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERV_ORDER] DROP CONSTRAINT FK_FH_SERV__REF_45543_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERV__REF_45548_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERV_ORDER] DROP CONSTRAINT FK_FH_SERV__REF_45548_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERVI_REF_30275_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERVICE] DROP CONSTRAINT FK_FH_SERVI_REF_30275_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERVI_REF_30276_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERVICE] DROP CONSTRAINT FK_FH_SERVI_REF_30276_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERVI_REF_30281_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERVICE] DROP CONSTRAINT FK_FH_SERVI_REF_30281_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERVI_REF_30310_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERVICE_ATTENDEES] DROP CONSTRAINT FK_FH_SERVI_REF_30310_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERVI_REF_45527_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERVICE_SCHEDULE] DROP CONSTRAINT FK_FH_SERVI_REF_45527_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERVI_REF_30315_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERVICE_CODES] DROP CONSTRAINT FK_FH_SERVI_REF_30315_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERVI_REF_23912_FH_SERVI]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERVICE_ATTENDEES] DROP CONSTRAINT FK_FH_SERVI_REF_23912_FH_SERVI
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_DC_TR_REF_45644_FH_SERV_]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_DC_TRACKING] DROP CONSTRAINT FK_FH_DC_TR_REF_45644_FH_SERV_
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_SERV__REF_40133_FH_SERV_]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_SERV_ORDER] DROP CONSTRAINT FK_FH_SERV__REF_40133_FH_SERV_
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_PC_REF_31577_FH_VA_BE]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_PCA_SECTION] DROP CONSTRAINT FK_FH_VA_PC_REF_31577_FH_VA_BE
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_AW_REF_31542_FH_VA_CO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_AWARDS] DROP CONSTRAINT FK_FH_VA_AW_REF_31542_FH_VA_CO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_MA_REF_31562_FH_VA_CO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_MARKER_APP] DROP CONSTRAINT FK_FH_VA_MA_REF_31562_FH_VA_CO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_MA_REF_31565_FH_VA_CO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_MARKER_APP] DROP CONSTRAINT FK_FH_VA_MA_REF_31565_FH_VA_CO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_SR_REF_31523_FH_VA_CO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_SRV_DATA] DROP CONSTRAINT FK_FH_VA_SR_REF_31523_FH_VA_CO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_SR_REF_31531_FH_VA_CO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_SRV_DATA] DROP CONSTRAINT FK_FH_VA_SR_REF_31531_FH_VA_CO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_WA_REF_31545_FH_VA_CO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_WAR_SERVICE] DROP CONSTRAINT FK_FH_VA_WA_REF_31545_FH_VA_CO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_CO_REF_31374_FH_VA_CO]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_CODES] DROP CONSTRAINT FK_FH_VA_CO_REF_31374_FH_VA_CO
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_SR_REF_31518_FH_VA_MA]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_SRV_DATA] DROP CONSTRAINT FK_FH_VA_SR_REF_31518_FH_VA_MA
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_AW_REF_31536_FH_VA_SR]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_AWARDS] DROP CONSTRAINT FK_FH_VA_AW_REF_31536_FH_VA_SR
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_BE_REF_31572_FH_VA_SR]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_BENEFITS_APP] DROP CONSTRAINT FK_FH_VA_BE_REF_31572_FH_VA_SR
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_FL_REF_31552_FH_VA_SR]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_FLAG_APP] DROP CONSTRAINT FK_FH_VA_FL_REF_31552_FH_VA_SR
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_MA_REF_31557_FH_VA_SR]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_MARKER_APP] DROP CONSTRAINT FK_FH_VA_MA_REF_31557_FH_VA_SR
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_VA_WA_REF_31537_FH_VA_SR]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_VA_WAR_SERVICE] DROP CONSTRAINT FK_FH_VA_WA_REF_31537_FH_VA_SR
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_GPL_ITEM_REF_17478_GPL]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[GPL_ITEM_VERSION] DROP CONSTRAINT FK_GPL_ITEM_REF_17478_GPL
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_GPL_ITEM_REF_45633_GPL_GROU]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[GPL_ITEM_MASTER] DROP CONSTRAINT FK_GPL_ITEM_REF_45633_GPL_GROU
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_GPL_ITEM_REF_47091_GPL_ITEM]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[GPL_ITEM_MASTER] DROP CONSTRAINT FK_GPL_ITEM_REF_47091_GPL_ITEM
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_GPL_ITEM_REF_47099_GPL_ITEM]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[GPL_ITEM_MASTER] DROP CONSTRAINT FK_GPL_ITEM_REF_47099_GPL_ITEM
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTA_REF_17494_GPL_ITEM]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTAIN] DROP CONSTRAINT FK_FH_CONTA_REF_17494_GPL_ITEM
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_GPL_ITEM_REF_17479_GPL_ITEM]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[GPL_ITEM_VERSION] DROP CONSTRAINT FK_GPL_ITEM_REF_17479_GPL_ITEM
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_GPL_ITEM_REF_17473_GPL_ITEM]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[GPL_ITEM_MASTER] DROP CONSTRAINT FK_GPL_ITEM_REF_17473_GPL_ITEM
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_PKG_PL_I_REF_17557_GPL_ITEM]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PKG_PL_ITEM] DROP CONSTRAINT FK_PKG_PL_I_REF_17557_GPL_ITEM
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_GPL_REF_45620_GPL_STAT]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[GPL] DROP CONSTRAINT FK_GPL_REF_45620_GPL_STAT
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_GPL_REF_58500_GPL_STAT]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[GPL] DROP CONSTRAINT FK_GPL_REF_58500_GPL_STAT
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTR_REF_22179_PKG_PL]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTRACT] DROP CONSTRAINT FK_FH_CONTR_REF_22179_PKG_PL
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_PKG_PL_I_REF_17542_PKG_PL]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[PKG_PL_ITEM] DROP CONSTRAINT FK_PKG_PL_I_REF_17542_PKG_PL
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTR_REF_48137_PKG_PL_I]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTRACT_ITEM] DROP CONSTRAINT FK_FH_CONTR_REF_48137_PKG_PL_I
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_FH_CONTR_REF_48142_PKG_PL_I]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[FH_CONTRACT_ITEM] DROP CONSTRAINT FK_FH_CONTR_REF_48142_PKG_PL_I
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FK_TAP_REVE_REFERENCE_TAP_REVE]') and OBJECTPROPERTY(id, N'IsForeignKey') = 1)
ALTER TABLE [dbo].[TAP_REVENUE_LEADS] DROP CONSTRAINT FK_TAP_REVE_REFERENCE_TAP_REVE
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CASE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CASE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CASE_PHOTO]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CASE_PHOTO]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CASE_STATUS_HISTORY]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CASE_STATUS_HISTORY]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CASE_STATUS_TYPE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CASE_STATUS_TYPE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CASE_VITALS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CASE_VITALS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CONTAIN]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CONTAIN]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CONTAIN_CODE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CONTAIN_CODE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CONTAIN_CODE_GROUP]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CONTAIN_CODE_GROUP]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CONTRACT]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CONTRACT]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CONTRACT_CHG_HISTORY]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CONTRACT_CHG_HISTORY]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CONTRACT_INS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CONTRACT_INS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CONTRACT_ITEM]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CONTRACT_ITEM]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_CONTRACT_STATUS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_CONTRACT_STATUS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_DC_TRACKING]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_DC_TRACKING]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_DISPOSITION]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_DISPOSITION]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_LAB_CODES]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_LAB_CODES]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_LAB_CODE_GROUP]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_LAB_CODE_GROUP]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_LAB_DATA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_LAB_DATA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_LAB_INJECTIONS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_LAB_INJECTIONS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_LAB_VESSELS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_LAB_VESSELS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_OBITUARY]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_OBITUARY]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_OBIT_PRT_SRC]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_OBIT_PRT_SRC]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_PALLBEARERS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_PALLBEARERS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_SERVICE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_SERVICE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_SERVICE_ATTENDEES]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_SERVICE_ATTENDEES]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_SERVICE_CODES]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_SERVICE_CODES]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_SERVICE_CODE_GROUPS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_SERVICE_CODE_GROUPS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_SERVICE_SCHEDULE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_SERVICE_SCHEDULE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_SERVICE_SURVIVORS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_SERVICE_SURVIVORS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_SERV_ORDER]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_SERV_ORDER]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_SERV_ORDER_ITEM]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_SERV_ORDER_ITEM]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_AWARDS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_AWARDS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_BENEFITS_APP]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_BENEFITS_APP]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_BURIAL_ALLOW]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_BURIAL_ALLOW]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_CODES]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_CODES]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_CODE_GROUPS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_CODE_GROUPS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_FLAG_APP]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_FLAG_APP]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_MARKER_APP]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_MARKER_APP]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_MASTER]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_MASTER]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_PCA_SECTION]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_PCA_SECTION]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_SRV_DATA]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_SRV_DATA]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[FH_VA_WAR_SERVICE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[FH_VA_WAR_SERVICE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GPL]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[GPL]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GPL_GROUP]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[GPL_GROUP]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GPL_ITEM_IMAGE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[GPL_ITEM_IMAGE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GPL_ITEM_MASTER]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[GPL_ITEM_MASTER]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GPL_ITEM_TYPE]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[GPL_ITEM_TYPE]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GPL_ITEM_VERSION]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[GPL_ITEM_VERSION]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[GPL_STATUS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[GPL_STATUS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PKG_PL]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PKG_PL]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[PKG_PL_ITEM]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[PKG_PL_ITEM]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TAP_REVENUE_LEADS]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TAP_REVENUE_LEADS]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[TAP_REVENUE_LEADS_GRP]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[TAP_REVENUE_LEADS_GRP]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[pbcatcol]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[pbcatcol]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[pbcatedt]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[pbcatedt]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[pbcatfmt]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[pbcatfmt]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[pbcattbl]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[pbcattbl]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[pbcatvld]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].[pbcatvld]
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[''Utilities - Gas$'']') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].['Utilities - Gas$']
GO

if exists (select * from dbo.sysobjects where id = object_id(N'[dbo].[''Utilities - Water$'']') and OBJECTPROPERTY(id, N'IsUserTable') = 1)
drop table [dbo].['Utilities - Water$']
GO

