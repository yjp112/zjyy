/*--通知设备显示什么信息--*/
/*--参数格式 string arg = "信息内容"--*/
function NotifyInfoToUnity(arg)
{
	if(isNaN(arg))
	{
		arg = "请初始化一个值"
	}
	alert( arg );
	u.getUnity().SendMessage("Main Camera","NotifyDeviceString",arg);
}
/*--通知设备当前状态（颜色，alpha）--*/
/*--参数格式 string arg = "color,alpha"--值的组成顺序不能乱*/
function NotifyAttributeToUnity(arg)
{
	alert( arg );
	u.getUnity().SendMessage("Main Camera","NotifyDeviceState",arg);
}
/*--场景获取数据方法>*/
function getMachineInfo(arg) 
{ 
	alert( arg );
} 