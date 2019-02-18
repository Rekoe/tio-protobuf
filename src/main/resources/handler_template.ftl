package ${packagePath};

import org.tio.core.ChannelContext;

import com.onemena.game.annotation.HandlerMapping;
import com.onemena.game.proto.${className};

/**
 * @version 1.0
 * @since 2018/1/29
 */
@HandlerMapping("${className}")
public class ${className}Handler extends AbstractDataHandler<${className}> {

    @Override
    public void onEvent(${className} ${lowerClassName}, ChannelContext ctx) throws Exception {

    }
}