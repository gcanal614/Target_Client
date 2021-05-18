package love.target.command.commands;

import com.utils.string.StringUtils;
import love.target.Wrapper;
import love.target.command.Command;
import love.target.mod.Mod;
import love.target.mod.value.Value;
import love.target.mod.value.values.*;

public class ModValueSetCommand extends Command {
    private final Mod mod;

    public ModValueSetCommand(Mod mod) {
        super(mod.getName().toLowerCase(), "", false);
        this.mod = mod;
    }

    @Override
    public void run(String[] args) {
        if (args.length == 2) {
            String valueName = args[0];
            String value = args[1];

            showNoValueLabel:
            {
                for (Value<?> v : mod.getValues()) {
                    if (v.getName().equalsIgnoreCase(valueName)) {
                        switch (v.getValueType()) {
                            case BOOLEAN_VALUE:
                                if (value.equalsIgnoreCase("true")) {
                                    ((BooleanValue) v).setValue(true);
                                    Wrapper.sendMessage("成功将" + v.getName() + " 设置成true");
                                } else if (value.equalsIgnoreCase("false")) {
                                    ((BooleanValue) v).setValue(false);
                                    Wrapper.sendMessage("成功将" + v.getName() + " 设置成false");
                                } else {
                                    Wrapper.sendMessage("请使用布尔值");
                                }
                                break;
                            case NUMBER_VALUE:
                                if (StringUtils.isNumber(value)) {
                                    NumberValue numberValue = (NumberValue) v;
                                    double doubleValue = Double.parseDouble(value);

                                    if (doubleValue < numberValue.getMinValue()) {
                                        Wrapper.sendMessage(value + "太小了!");
                                    } else if (doubleValue > numberValue.getMaxValue()) {
                                        Wrapper.sendMessage(value + "太大了!");
                                    } else {
                                        numberValue.setValue(doubleValue);
                                        Wrapper.sendMessage("成功将" + v.getName() + "设置为" + value);
                                    }
                                } else {
                                    Wrapper.sendMessage("你输入的不是一个数字!!!!!!");
                                }
                                break;
                            case MODE_VALUE:
                                ModeValue modeValue = (ModeValue) v;
                                showNoModLabel:
                                {
                                    for (String mod : modeValue.getModes()) {
                                        if (mod.equalsIgnoreCase(value)) {
                                            modeValue.setValue(value);
                                            Wrapper.sendMessage("成功设置" + modeValue.getValue() + "为" + value);
                                            break showNoModLabel;
                                        }
                                    }
                                    Wrapper.sendMessage("没找到这个Mode " + valueName);
                                }
                                break;
                            case TEXT_VALUE:
                                ((TextValue) v).setValue(value);
                                Wrapper.sendMessage("成功将" + v.getName() + "设置为" + value);
                                break;
                            case COLOR_VALUE:
                                if (StringUtils.isNumber(value)) {
                                    ((ColorValue) v).setValue(Integer.parseInt(value));
                                    Wrapper.sendMessage("成功将" + v.getName() + "设置为" + value);
                                } else {
                                    Wrapper.sendMessage("你输入的不是一个数字!!!!!!");
                                }
                                break;
                        }
                        break showNoValueLabel;
                    }
                }

                Wrapper.sendMessage("Value not found " + valueName);
            }
        } else {
            Wrapper.sendMessage("Try -" + mod.getName().toLowerCase() + " valueName value");
        }

        super.run(args);
    }
}
