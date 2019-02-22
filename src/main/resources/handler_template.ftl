package ${packagePath};

import org.nutz.ioc.loader.annotation.IocBean;
import org.tio.core.ChannelContext;

import com.onemena.game.annotation.HandlerMapping;
import com.onemena.game.proto.${className};

/**
 * @version 1.0
 * @since 2018/1/29
 */
 @IocBean
@HandlerMapping("${className}")
public class ${className}Handler extends AbstractDataHandler<${className}> {

    @Override
    public void onEvent(${className} ${lowerClassName}, ChannelContext ctx) throws Exception {

    }
}