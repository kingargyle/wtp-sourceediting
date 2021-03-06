/**********************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others. All rights reserved.   This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: 
 * IBM - Initial API and implementation
 **********************************************************************/
package org.eclipse.jdi.internal.spy;

import org.eclipse.osgi.util.NLS;

public class TcpIpSpyMessages extends NLS {
	private static final String BUNDLE_NAME = "org.eclipse.jdi.internal.spy.TcpIpSpyMessages";//$NON-NLS-1$

	public static String JdwpConversation_Attempt_to_overwrite_command_with__0__1;
	public static String JdwpConversation_Attempt_to_overwrite_reply_with__0__2;
	public static String TcpipSpy_usage__TcpipSpy__client_port___server_host___server_port____output_file___1;
	public static String TcpipSpy_Writing_output_to__0__2;
	public static String TcpipSpy_Could_not_open__0____Using_stdout_instead_3;
	public static String TcpipSpy_Caught_exception___0__5;
	public static String VerbosePacketStream_From_VM_1;
	public static String VerbosePacketStream_From_Debugger_2;
	public static String VerbosePacketStream_Packet_ID__3;
	public static String VerbosePacketStream_Length__4;
	public static String VerbosePacketStream_Flags__5;
	public static String VerbosePacketStream___REPLY_to__0___6;
	public static String VerbosePacketStream___COMMAND__7;
	public static String VerbosePacketStream_Command_set__8;
	public static String VerbosePacketStream_Command__9;
	public static String VerbosePacketStream_Error__10;
	public static String VerbosePacketStream_NOT_MANAGED_COMMAND_11;
	public static String VerbosePacketStream_Unknown_command____0___1__12;
	public static String VerbosePacketStream_Type_tag__19;
	public static String VerbosePacketStream_unknow_20;
	public static String VerbosePacketStream_Status__21;
	public static String VerbosePacketStream_Modifiers__23;
	public static String VerbosePacketStream_Invocation_Options__24;
	public static String VerbosePacketStream_Thread_status__25;
	public static String VerbosePacketStream_Suspend_status__27;
	public static String VerbosePacketStream_Event_kind__28;
	public static String VerbosePacketStream_Suspend_policy__30;
	public static String VerbosePacketStream_Step_depth__32;
	public static String VerbosePacketStream_Step_size__34;
	public static String VerbosePacketStream_VM_Description__36;
	public static String VerbosePacketStream_JDWP_Major_Version__37;
	public static String VerbosePacketStream_JDWP_Minor_Version__38;
	public static String VerbosePacketStream_VM_Version__39;
	public static String VerbosePacketStream_VM_Name__40;
	public static String VerbosePacketStream_Class_signature__41;
	public static String VerbosePacketStream_Classes_count__42;
	public static String VerbosePacketStream_Type_id__43;
	public static String VerbosePacketStream_Threads_count__47;
	public static String VerbosePacketStream_Thread_id__48;
	public static String VerbosePacketStream_Field_ID_size__51;
	public static String VerbosePacketStream_Method_ID_size__52;
	public static String VerbosePacketStream_Object_ID_size__53;
	public static String VerbosePacketStream_Reference_type_ID_size__54;
	public static String VerbosePacketStream_Frame_ID_size__55;
	public static String VerbosePacketStream_Exit_code__56;
	public static String VerbosePacketStream_String__57;
	public static String VerbosePacketStream_String_id__58;
	public static String VerbosePacketStream_Can_watch_field_modification__59;
	public static String VerbosePacketStream_can_watch_field_access__60;
	public static String VerbosePacketStream_Can_get_bytecodes__61;
	public static String VerbosePacketStream_Can_get_synthetic_attribute__62;
	public static String VerbosePacketStream_Can_get_owned_monitor_info__63;
	public static String VerbosePacketStream_Can_get_cur__contended_monitor__64;
	public static String VerbosePacketStream_Can_get_monitor_info__65;
	public static String VerbosePacketStream_Base_directory__66;
	public static String VerbosePacketStream_Classpaths_count__67;
	public static String VerbosePacketStream_Classpath__68;
	public static String VerbosePacketStream_Bootclasspaths_count__69;
	public static String VerbosePacketStream_Bootclasspath__70;
	public static String VerbosePacketStream_Requests_Count__71;
	public static String VerbosePacketStream_Object_id__72;
	public static String VerbosePacketStream_References_count__73;
	public static String VerbosePacketStream_Can_redefine_classes__74;
	public static String VerbosePacketStream_Can_add_method__75;
	public static String VerbosePacketStream_Can_unrestrictedly_rd__classes__76;
	public static String VerbosePacketStream_Can_pop_frames__77;
	public static String VerbosePacketStream_Can_use_instance_filters__78;
	public static String VerbosePacketStream_Can_get_source_debug_extension__79;
	public static String VerbosePacketStream_Can_request_VMDeath_event__80;
	public static String VerbosePacketStream_Can_set_default_stratum__81;
	public static String VerbosePacketStream_Reserved__82;
	public static String VerbosePacketStream_Types_count__99;
	public static String VerbosePacketStream_Classfile_length__101;
	public static String VerbosePacketStream_Class_bytes__102;
	public static String VerbosePacketStream_skipped_103;
	public static String VerbosePacketStream_Stratum_id__104;
	public static String VerbosePacketStream_Signature__106;
	public static String VerbosePacketStream_ClassLoader_id__107;
	public static String VerbosePacketStream_Fields_count__108;
	public static String VerbosePacketStream_Field_id__109;
	public static String VerbosePacketStream_Name__110;
	public static String VerbosePacketStream_Methods_count__112;
	public static String VerbosePacketStream_Method_id__113;
	public static String VerbosePacketStream_Values_count__119;
	public static String VerbosePacketStream_Value__120;
	public static String VerbosePacketStream_Source_file__121;
	public static String VerbosePacketStream_Interfaces_count__124;
	public static String VerbosePacketStream_Interface_type_id__125;
	public static String VerbosePacketStream_Class_object_id__126;
	public static String VerbosePacketStream_Extension__127;
	public static String VerbosePacketStream_Class_type_id__128;
	public static String VerbosePacketStream_Superclass_type_id__129;
	public static String VerbosePacketStream_List_of_values__NOT_MANAGED_132;
	public static String VerbosePacketStream_Arguments_count__136;
	public static String VerbosePacketStream_Argument__137;
	public static String VerbosePacketStream_Return_value__138;
	public static String VerbosePacketStream_Exception_object_id__139;
	public static String VerbosePacketStream_New_object_id__140;
	public static String VerbosePacketStream_Array_type_id__142;
	public static String VerbosePacketStream_Length__143;
	public static String VerbosePacketStream_New_array_id__144;
	public static String VerbosePacketStream_Lowest_valid_code_index__147;
	public static String VerbosePacketStream_Highest_valid_code_index__148;
	public static String VerbosePacketStream_Number_of_lines__149;
	public static String VerbosePacketStream_Line_code_Index__150;
	public static String VerbosePacketStream_Line_number__151;
	public static String VerbosePacketStream_Nb_of_slots_used_by_all_args__152;
	public static String VerbosePacketStream_Nb_of_variables__153;
	public static String VerbosePacketStream_First_code_index__154;
	public static String VerbosePacketStream_Variable_name__155;
	public static String VerbosePacketStream_Variable_type_signature__156;
	public static String VerbosePacketStream_Code_index_length__157;
	public static String VerbosePacketStream_Slot_id__158;
	public static String VerbosePacketStream_Nb_of_bytes__159;
	public static String VerbosePacketStream_Method_bytes__160;
	public static String VerbosePacketStream_Is_obsolete__162;
	public static String VerbosePacketStream_Owner_thread_id__173;
	public static String VerbosePacketStream_Entry_count__174;
	public static String VerbosePacketStream_Nb_of_waiters__175;
	public static String VerbosePacketStream_Waiting_thread_id__176;
	public static String VerbosePacketStream_Is_collected__185;
	public static String VerbosePacketStream_String_object_id__186;
	public static String VerbosePacketStream_Thread_group_id__190;
	public static String VerbosePacketStream_First_frame__192;
	public static String VerbosePacketStream_Number_of_frame__193;
	public static String VerbosePacketStream_Frames_count__194;
	public static String VerbosePacketStream_Frame_id__195;
	public static String VerbosePacketStream_Monitors_count__197;
	public static String VerbosePacketStream_Monitor_object_id__198;
	public static String VerbosePacketStream_Suspend_count__202;
	public static String VerbosePacketStream_Parent_thread_group_id__205;
	public static String VerbosePacketStream_Child_threads_count__206;
	public static String VerbosePacketStream_Child_thread_id__207;
	public static String VerbosePacketStream_Child_group_threads_count__208;
	public static String VerbosePacketStream_Child_group_thread_id__209;
	public static String VerbosePacketStream_Array_object_id__210;
	public static String VerbosePacketStream_First_index__213;
	public static String VerbosePacketStream_Length__214;
	public static String VerbosePacketStream_Class_loader_object_id__219;
	public static String VerbosePacketStream_Modifiers_count__222;
	public static String VerbosePacketStream_Modifier_kind__223;
	public static String VerbosePacketStream___Count__224;
	public static String VerbosePacketStream_Count__225;
	public static String VerbosePacketStream___Conditional__226;
	public static String VerbosePacketStream_Expression_id__227;
	public static String VerbosePacketStream___ThreadOnly__228;
	public static String VerbosePacketStream___ClassOnly__230;
	public static String VerbosePacketStream___ClassMatch__232;
	public static String VerbosePacketStream_Class_patern__233;
	public static String VerbosePacketStream___ClassExclude__234;
	public static String VerbosePacketStream_Class_patern__235;
	public static String VerbosePacketStream___LocationOnly__236;
	public static String VerbosePacketStream___ExceptionOnly__237;
	public static String VerbosePacketStream_Exception_type_id__238;
	public static String VerbosePacketStream_Caught__239;
	public static String VerbosePacketStream_Uncaught__240;
	public static String VerbosePacketStream___FieldOnly__241;
	public static String VerbosePacketStream_Declaring_type_id__242;
	public static String VerbosePacketStream___Step__244;
	public static String VerbosePacketStream___InstanceOnly__246;
	public static String VerbosePacketStream_Request_id__248;
	public static String VerbosePacketStream_Thread_object_id__250;
	public static String VerbosePacketStream_Slots_count__254;
	public static String VerbosePacketStream_Slot_index__255;
	public static String VerbosePacketStream_Signature_tag__256;
	public static String VerbosePacketStream_Values__263;
	public static String VerbosePacketStream___this___object_id__264;
	public static String VerbosePacketStream_Events_count__267;
	public static String VerbosePacketStream_Initial_thread_object_id__269;
	public static String VerbosePacketStream_Type_signature__275;
	public static String VerbosePacketStream_Status__276;
	public static String VerbosePacketStream_Second_byte_input_does_not_match_UTF_Specification_287;
	public static String VerbosePacketStream_Second_or_third_byte_input_does_not_mach_UTF_Specification__288;
	public static String VerbosePacketStream_Input_does_not_match_UTF_Specification_289;
	public static String VerbosePacketStream_Unable_to_parse_remaining_data_290;
	public static String VerbosePacketStream_Location__class_id__297;
	public static String VerbosePacketStream___________method_id__298;
	public static String VerbosePacketStream___________index__299;
	public static String VerbosePacketStream_Signature_byte__300;
	public static String VerbosePacketStream_Value_302;
	public static String VerbosePacketStream_Remaining_data__1;
	public static String TcpIpSpy_This_packet_is_marked_as_reply__but_there_is_no_command_with_the_same_id__1;
	public static String VerbosePacketStream_Generic_class_signature__405;
	public static String VerbosePacketStream_Generic_signature__422;
	public static String VerbosePacketStream_Variable_type_generic_signature__425;

	static {
		// load message values from bundle file
		NLS.initializeMessages(BUNDLE_NAME, TcpIpSpyMessages.class);
	}
}