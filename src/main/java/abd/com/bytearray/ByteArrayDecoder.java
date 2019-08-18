package abd.com.bytearray;

import java.util.ArrayList;
import java.util.List;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolDecoderAdapter;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import abd.com.common.Common;

public class ByteArrayDecoder extends ProtocolDecoderAdapter {

	@Override
	public void decode(IoSession session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

		int limit = in.limit();
		byte[] bytes = new byte[limit];

		in.get(bytes);

		List<Byte> list = Common.messageMap.get(session.getId());
		if (list == null) {
			list = new ArrayList<Byte>();
		}
		for (byte b : bytes) {
			list.add(b);
		}

		while (list.size() >= 26) {
			byte[] bArray = new byte[26];
			for(int i=0;i<26;i++){
				bArray[i] = (byte) list.get(i);
			}
			for(int i=0;i<26;i++){
				list.remove(0);
			}
			
			out.write(bArray);
		}
		
		if (list.size() > 0) {
			Common.messageMap.put(session.getId(), list);
		}
		

	}

}