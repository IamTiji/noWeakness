package com.tiji.noweakness;

import net.minecraft.text.ClickEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public class Translations {
    public static final Text RULE_TEXT = Text.literal("")
            .append(Text.literal("1. ").formatted(Formatting.GOLD))
            .append(Text.literal("네더 오버월드 1:1\n").formatted(Formatting.AQUA))
            .append(Text.literal("2. ").formatted(Formatting.GOLD))
            .append(Text.literal("드래곤 체력 600\n").formatted(Formatting.AQUA))
            .append(Text.literal("3. ").formatted(Formatting.GOLD))
            .append(Text.literal("월드 크기 4000x4000\n").formatted(Formatting.AQUA))
            .append(Text.literal("4. ").formatted(Formatting.GOLD))
            .append(Text.literal("날카 최대 레벨 8\n").formatted(Formatting.AQUA))
            .append(Text.literal("5. ").formatted(Formatting.GOLD))
            .append(Text.literal("힘 최대 레벨 7\n").formatted(Formatting.AQUA))
            .append(Text.literal("6. ").formatted(Formatting.GOLD))
            .append(Text.literal("공속 줄이는 인챈트\n").formatted(Formatting.AQUA))
            .append(Text.literal("   - ").formatted(Formatting.GOLD))
            .append(Text.literal("고대도시에서만 얻을 수 있음\n").formatted(Formatting.AQUA))
            .append(Text.literal("   - ").formatted(Formatting.GOLD))
            .append(Text.literal("최대 레벨 5, 최대 레벨에선 공속 없음\n").formatted(Formatting.AQUA))
            .append(Text.literal("   - ").formatted(Formatting.GOLD))
            .append(Text.literal("한번에 레벨 2까지만 생성\n").formatted(Formatting.AQUA))
            .append(Text.literal("7. ").formatted(Formatting.GOLD))
            .append(Text.literal("이름표 제거\n").formatted(Formatting.AQUA))
            .append(Text.literal("8. ").formatted(Formatting.GOLD))
            .append(Text.literal("드래곤 알은 보상 아이템으로 교환 가능 (/교환 또는 /convert); 다음 중 하나:\n").formatted(Formatting.AQUA))
            .append(Text.literal("   - ").formatted(Formatting.GOLD))
            .append(Text.literal("풀인첸 겉날개\n").formatted(Formatting.AQUA))
            .append(Text.literal("   - ").formatted(Formatting.GOLD))
            .append(Text.literal("네더라이트 5개\n").formatted(Formatting.AQUA))
            .append(Text.literal("   - ").formatted(Formatting.GOLD))
            .append(Text.literal("풀인첸 다이아 도끼 (날카 제외), 곡괭이, 삽\n").formatted(Formatting.AQUA))
            .append(Text.literal("9. ").formatted(Formatting.GOLD))
            .append(Text.literal("플레이어 공격 또는 공격을 당하고 10초 이내로 나갈 시 즉사\n").formatted(Formatting.AQUA))
            .append(Text.literal("10. ").formatted(Formatting.GOLD))
            .append(Text.literal("토템을 3개 이상 들고 있을 수 없음. (셜커 상자에 있는 토템은 제외)\n").formatted(Formatting.AQUA))
            .append(Text.literal("   - ").formatted(Formatting.GOLD))
            .append(Text.literal("3개 이상 들고 있을 시 경고\n").formatted(Formatting.AQUA))
            .append(Text.literal("   - ").formatted(Formatting.GOLD))
            .append(Text.literal("1분 이상 토템 3개 이상 소유한다면 즉사\n").formatted(Formatting.AQUA))
            .append(Text.literal("11. ").formatted(Formatting.GOLD))
            .append(Text.literal("죽을 시 최대 체력이 1칸 줄고 아이템에 체력 아이템이 드롭됨\n").formatted(Formatting.AQUA))
            .append(Text.literal("   - ").formatted(Formatting.GOLD))
            .append(Text.literal("아이탬을 들고 우클릭해 최대 체력으로 흡수").formatted(Formatting.AQUA))
            .append(Text.literal("   - ").formatted(Formatting.GOLD))
            .append(Text.literal("명령어를 통해 최대 체력을 아이탬으로 변환할 수 있음(/생체전환 또는 /extract)").formatted(Formatting.AQUA))
            .append(Text.literal("12. ").formatted(Formatting.GOLD))
            .append(Text.literal("랜덤 스폰\n").formatted(Formatting.AQUA))
            .append(Text.literal("13. ").formatted(Formatting.GOLD))
            .append(Text.literal("고대도시에서 노치 사과 확률 대폭 증가\n").formatted(Formatting.AQUA))
            .append(Text.literal("14. ").formatted(Formatting.GOLD))
            .append(Text.literal("TPA 명령어 사용 가능 (/tpa 또는 /티피요청)").formatted(Formatting.AQUA))
            .append(Text.literal("15. ").formatted(Formatting.GOLD))
            .append(Text.literal("벽 투과 (프리캠, 아이탬 하이라이트) 가능하게 하는 모드 금지").formatted(Formatting.AQUA))
            .append(Text.literal("16. ").formatted(Formatting.GOLD))
            .append(Text.literal("오토클릭커 (좌클릭, 클릭 간격이 200ms가 넘는 설정 한정) 금지").formatted(Formatting.AQUA));
    public static final Text NO_DRAGON_EGG_FAIL = Text.literal("드래곤 알을 손에 들고 있지 않습니다.").formatted(Formatting.RED);
    public static final Text CONVERT_ELYTRA_SUCCESS = Text.literal("드래곤 알을 겉날게로 교환하였습니다.");
    public static final Text CONVERT_NETHERITE_SUCCESS = Text.literal("드래곤 알을 네더라이트로 교환하였습니다.");
    public static final Text CONVERT_TOOL_SUCCESS = Text.literal("드래곤 알을 도구로 교환하였습니다.");
    public static final Text CONVERT_INFO = Text.literal("드래곤 알을 겉날게로 교환하려면 /교환 겉날게")
            .append(Text.literal("\n또는 네더라이트로 교환하려면 /교환 네더라이트"))
            .append(Text.literal("\n또는 도구로 교환하려면 /교환 도구"));
    public static final Text HEALTH_ITEM_NAME = Text.literal("농축된 체력");
    public static final Text HEALTH_ITEM_LORE = Text.literal("이 아이탬을 사용하여 최대 체력을 1칸 늘릴수 있습니다");
    public static final Text EXTRACT_HEALTH_NOT_ENOUGH_FAIL = Text.literal("최대 체력이 부족합니다").formatted(Formatting.RED);
    public static final Text TOO_MUCH_TOTEMS = Text.literal("토탬을 너무 많이 갖고 있습니다!").formatted(Formatting.RED);
    public static final Text PVP_TIME_END = Text.literal("PVP 중: ").formatted(Formatting.GOLD)
            .append(Text.literal("종료").formatted(Formatting.GREEN, Formatting.BOLD))
            .append(Text.literal(" | ").formatted(Formatting.WHITE))
            .append(Text.literal("PVP 종료까지: ").formatted(Formatting.GOLD))
            .append(Text.literal("0초").formatted(Formatting.AQUA));
    public static final Text SET_HOME_NOT_OVERWORLD_FAIL = Text.literal("이 명령어는 오버월드에서만 사용할 수 있습니다.").formatted(Formatting.RED);
    public static final Text TELEPORT_HOME_SUCCESS = Text.literal("성공적으로 집으로 순간이동 하였습니다.").formatted(Formatting.GREEN);
    public static final Text TELEPORT_HOME_NO_HOME_FAIL = Text.literal("집이 아직 지정되지 않았습니다.").formatted(Formatting.RED);
    public static final Text TELEPORT_HOME_IN_PVP_FAIL = Text.literal("PVP 중에는 이동할 수 없습니다.").formatted(Formatting.RED);
    public static final Text INFO_HOME_NO_HOME_FAIL = Text.literal("집이 아직 지정되지 않았습니다.").formatted(Formatting.RED);
    public static final Text TPA_ACCEPT_IN_PVP_FAIL = Text.literal("PVP 중에는 순간이동 요청을 할 수 없습니다.").formatted(Formatting.RED);
    public static final Text TPA_ACCEPT_TARGET_IN_PVP_FAIL = Text.literal("상대가 PVP하는 중에는 순간이동 요청을 할 수 없습니다.").formatted(Formatting.RED);
    public static final Text TPA_REQUEST_ALREADY_SENT_FAIL = Text.literal("이미 이 플레이어에게 순간이동 요청을 보냈습니다.").formatted(Formatting.RED);
    public static final Text TPA_REQUEST_NOT_FOUND_FAIL = Text.literal("아직 이 플레이어는 당신에게 요청을 보내지 않았습니다.").formatted(Formatting.RED);
    public static Text getPvpTimeWarning(int remainingTime) {
        return Text.literal("PVP 중: ").formatted(Formatting.GOLD)
                .append(Text.literal("! 나갈시 즉사 !").formatted(Formatting.DARK_RED, Formatting.BOLD))
                .append(Text.literal(" | ").formatted(Formatting.WHITE))
                .append(Text.literal("PVP 종료까지: ").formatted(Formatting.GOLD))
                .append(Text.literal(String.format("%d초", remainingTime)).formatted(Formatting.AQUA));
    }
    public static Text getHomeCommandSuccess(double x, double y, double z) {
        return Text.literal("집을 ").formatted(Formatting.GREEN)
                .append(Text.literal(String.valueOf(x))).formatted(Formatting.AQUA)
                .append(" ")
                .append(Text.literal(String.valueOf(y))).formatted(Formatting.AQUA)
                .append(" ")
                .append(Text.literal(String.valueOf(z))).formatted(Formatting.AQUA)
                .append(" 로 설정하였습니다.").formatted(Formatting.GREEN);
    }
    public static Text getHomeInfoCommandResult(double x, double y, double z) {
        return Text.literal("집: ").formatted(Formatting.GOLD)
                .append(Text.literal(String.valueOf(x))).formatted(Formatting.AQUA)
                .append(" ")
                .append(Text.literal(String.valueOf(y))).formatted(Formatting.AQUA)
                .append(" ")
                .append(Text.literal(String.valueOf(z))).formatted(Formatting.AQUA);
    }
    public static Text getTpaRequestResponse(String name) {
        return Text.literal(name).formatted(Formatting.AQUA)
                .append(Text.literal("에게 순간이동 요청을 보냈습니다").formatted(Formatting.GREEN));
    }
    public static Text getTpaRequestedText(String name) {
        return Text.literal(name).formatted(Formatting.AQUA)
                .append(Text.literal("의 순간이동 요청을 받았습니다").formatted(Formatting.GREEN))
                .append(Text.literal("\n ( /티피요청 수락 ").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa 수락 " + name))).formatted(Formatting.GREEN))
                .append(Text.literal(name).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa 수락 " + name))).formatted(Formatting.GREEN))
                .append(Text.literal(" )").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa 수락 " + name))).formatted(Formatting.GREEN))
                .append(Text.literal(" | ").formatted(Formatting.GRAY))
                .append(Text.literal(" ( /티피요청 거절 ").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa 거절 " + name))).formatted(Formatting.RED))
                .append(Text.literal(name).setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa 거절 " + name))).formatted(Formatting.RED))
                .append(Text.literal(" )").setStyle(Style.EMPTY.withClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tpa 거절 " + name))).formatted(Formatting.RED));
    }
    public static Text getTpaRequestDeclinedTargetText(String name) {
        return Text.literal(name).formatted(Formatting.AQUA)
                .append(Text.literal("님의 순간이동 요청을 거절하였습니다").formatted(Formatting.RED));
    }
    public static Text getTpaRequestDeclinedSenderText(String name) {
        return Text.literal(name).formatted(Formatting.AQUA)
                .append(Text.literal("님이 요청을 거절하였습니다.").formatted(Formatting.RED));
    }
    public static Text getTpaRequestAcceptedTargetText(String name){
        return Text.literal(name).formatted(Formatting.AQUA)
                .append(Text.literal("님의 순간이동 요청을 수락하였습니다").formatted(Formatting.GREEN));
    }
    public static Text getTpaRequestAcceptedSenderText(String name){
        return Text.literal(name).formatted(Formatting.AQUA)
                .append(Text.literal("님이 요청을 수락하였습니다.").formatted(Formatting.GREEN));
    }
    public static Text getTpaRequestCancelledTargetText(String name){
        return Text.literal(name).formatted(Formatting.AQUA)
                .append(Text.literal("님이 요청을 취소하셨습니다.").formatted(Formatting.RED));
    }
    public static Text getTpaRequestCancelledSenderText(String name){
        return Text.literal(name).formatted(Formatting.AQUA)
                .append(Text.literal("님에게 보낸 요청을 취소하였습니다.").formatted(Formatting.RED));
    }
}

