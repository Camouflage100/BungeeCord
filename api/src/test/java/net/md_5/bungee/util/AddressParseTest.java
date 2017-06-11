package net.md_5.bungee.util;

import lombok.RequiredArgsConstructor;
import net.md_5.bungee.Util;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.Collection;

@RequiredArgsConstructor @RunWith(Parameterized.class) public class AddressParseTest {

    private final String line;
    private final String host;
    private final int port;

    @Parameters public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {{"127.0.0.1", "127.0.0.1", Util.DEFAULT_PORT},
            {"127.0.0.1:1337", "127.0.0.1", 1337}, {"[::1]", "0:0:0:0:0:0:0:1", Util.DEFAULT_PORT},
            {"[0:0:0:0::1]", "0:0:0:0:0:0:0:1", Util.DEFAULT_PORT},
            {"[0:0:0:0:0:0:0:1]", "0:0:0:0:0:0:0:1", Util.DEFAULT_PORT},
            {"[::1]:1337", "0:0:0:0:0:0:0:1", 1337}, {"[0:0:0:0::1]:1337", "0:0:0:0:0:0:0:1", 1337},
            {"[0:0:0:0:0:0:0:1]:1337", "0:0:0:0:0:0:0:1", 1337}});
    }

    @Test public void test() {
        InetSocketAddress parsed = Util.getAddr(line);
        Assert.assertEquals(host, parsed.getHostString());
        Assert.assertEquals(port, parsed.getPort());
    }
}
