<HTML>
<HEAD><TITLE>������վ</TITLE></HEAD>
<BODY>
<OBJECT id=rd codeBase=/comRD800.dll classid="clsid:638B238E-EB84-4933-B3C8-854B86140668"></OBJECT>

<script language=javascript>

function OnTest()
{
///////////////////////////////////////////////////////////////////////////////
//����Ϊ�ǽӴ�ʽ��д�������������� for javascript
//ע�������������dc_disp_str��ֻ�е��豸����Ҫ��������������ʾ��ʱ����Ч
//����ϸ�İ����ɲ���32λ��̬���Ӧ�ĺ���ʹ��˵��
///////////////////////////////////////////////////////////////////////////////

var st; //��Ҫ���ڷ���ֵ
var lSnr; //������ȡ���кţ�����javascriptֻ�ǵ���dc_card������һ����ʱ����

//��ʼ���˿�
//��һ������Ϊ0��ʾCOM1��Ϊ1��ʾCOM2���Դ�����
//�ڶ�������ΪͨѶ������
st = rd.dc_init(100, 115200);
if(st <= 0) //����ֵС�ڵ���0��ʾʧ��
{
    alert("dc_init error!");
	return;
}


alert("dc_init ok!");

//Ѱ�����ܷ����ڹ���������ĳ�ſ������к�
//��һ������һ������Ϊ0����ʾIDLEģʽ��һ��ֻ��һ�ſ�����
//�ڶ���������javascriptֻ�ǵ���dc_card������һ����ʱ����������vbscript�е��ú�����ȷ�������к�
st = rd.dc_card(0, lSnr);
if(st != 0) //����ֵС��0��ʾʧ��
{
    alert("dc_card error!");
    rd.dc_exit();
	return;
}
alert("dc_card ok!");
alert(rd.get_bstrRBuffer); //���к�Ϊrd.get_bstrRBuffer��һ���в�����ʾ�ַ�����
alert(rd.get_bstrRBuffer_asc); //���к�ʮ������ascll���ַ�����ʾΪrd.get_bstrRBuffer_asc

//������װ���дģ��RAM��
//��һ������Ϊװ������ģʽ
//�ڶ�������Ϊ������
rd.put_bstrSBuffer_asc = "FFFFFFFFFFFF"; //�ڵ���dc_load_key����ǰ����������rd.put_bstrSBuffer��rd.put_bstrSBuffer_asc
st = rd.dc_load_key(0, 0);
if(st != 0) //����ֵС��0��ʾʧ��
{
    alert("dc_load_key error!");
    rd.dc_exit();
    return;
}
alert("dc_load_key ok!");

//�˶����뺯��
//��һ������Ϊ������֤ģʽ
//�ڶ�������Ϊ������
st = rd.dc_authentication(0, 0);
if(st != 0) //����ֵС��0��ʾʧ��
{
    alert("dc_authentication error!");
    rd.dc_exit();
    return;
}
alert("dc_authentication ok!");

//����д�����ݣ�һ�α���дһ����
//��һ������Ϊ���ַ
//�ڵ���dc_write����ǰ����������rd.put_bstrSBuffer��rd.put_bstrSBuffer_asc
rd.put_bstrSBuffer_asc = "31323334353637383930313233343536";
st = rd.dc_write(2);
if(st != 0) //����ֵС��0��ʾʧ��
{
    alert("dc_write error!");
    rd.dc_exit();
    return;
}
alert("dc_write ok!");

//��ȡ�������ݣ�һ�α����һ����
//��һ������Ϊ���ַ
//ȡ�������ݷ������Է���rd.put_bstrSBuffer��������ʾ����rd.put_bstrSBuffer_asc��ʮ������ascll���ַ�����ʾ����
st = rd.dc_read(2);
if(st != 0) //����ֵС��0��ʾʧ��
{
    alert("dc_read error!");
    rd.dc_exit();
    return;
}
alert("dc_read ok!");
alert(rd.get_bstrRBuffer);
alert(rd.get_bstrRBuffer_asc);

//���ö�д��ʱ��
//04�� ������ 9�� 9�� 11�� 20�� 50��
rd.put_bstrSBuffer_asc = "04040909112050";
st = rd.dc_settime();
if(st != 0) //����ֵС��0��ʾʧ��
{
    alert("dc_settime error!");
    rd.dc_exit();
    return;
}
alert("dc_settime ok!");

//�ڶ�д�������������ʾ����
rd.put_bstrSBuffer = "12345678\0"; //��ʾ12345678��ע���ַ�������븽��һ��ascllΪ0x00��������;
st = rd.dc_disp_str();
if(st != 0) //����ֵС��0��ʾʧ��
{
    alert("dc_disp_str error!");
    rd.dc_exit();
    return;
}
alert("dc_disp_str ok!");

//����
//��һ������Ϊ����ʱ�䣬��λ��10����
st = rd.dc_beep(50);
if(st != 0) //����ֵС��0��ʾʧ��
{
    alert("dc_beep error!");
    rd.dc_exit();
    return;
}
alert("dc_beep ok!");

//�رն˿�
st = rd.dc_exit();
if(st != 0) //����ֵС��0��ʾʧ��
{
    alert("dc_exit error!");
    return;
}
alert("dc_exit ok!");

}

</script>

<!-- Insert HTML here -->
<INPUT id=test type=button value=test onclick=OnTest()></BODY>
</BODY>
</HTML>
