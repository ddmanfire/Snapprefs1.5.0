package com.marz.snapprefs;

import android.content.Context;
import android.content.res.XModuleResources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.callMethod;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;
import static de.robv.android.xposed.XposedHelpers.getObjectField;
import static de.robv.android.xposed.XposedHelpers.setAdditionalInstanceField;

public class Premium {
    static void initPremium(final XC_LoadPackage.LoadPackageParam lpparam, final XModuleResources modRes, final Context snapContext) {
        Class<?> ate = findClass("ate", lpparam.classLoader);
        findAndHookMethod("aty", lpparam.classLoader, "d", XC_MethodReplacement.returnConstant(true));
        findAndHookMethod("ate", lpparam.classLoader, "y", XC_MethodReplacement.returnConstant(false));
        findAndHookMethod("ate", lpparam.classLoader, "u", XC_MethodReplacement.returnConstant(false));
        //Disable snapchat being viewed
        findAndHookMethod("ate", lpparam.classLoader, "B", XC_MethodReplacement.returnConstant(false));

        /*findAndHookMethod("ate", lpparam.classLoader, "c", new XC_MethodHook() {

                }); */

        //findAndHookMethod("ata", lpparam.classLoader, "b", XC_MethodReplacement.returnConstant(new ArrayList().add(2)));

        //auto save chats
        //findAndHookMethod("auf", lpparam.classLoader, "o", XC_MethodReplacement.returnConstant(true));

        //remove long press to save
        //findAndHookMethod("aua", lpparam.classLoader, "C",XC_MethodReplacement.DO_NOTHING);

        //findAndHookMethod("com.snapchat.android.trophies.TrophyCaseFragment", lpparam.classLoader, "onClick", View.class, XC_MethodReplacement.DO_NOTHING);


        findAndHookMethod("com.snapchat.android.trophies.TrophyCaseFragment", lpparam.classLoader, "onClick", View.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param)
                    throws Throwable {
                Toast.makeText(snapContext, "Trophy",
                        Toast.LENGTH_LONG).show();
                //logging("Snap Prefs: Removing Best-friends");
            }
        });

        findAndHookMethod("com.snapchat.android.fragments.stories.StoriesFragment", lpparam.classLoader, "onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class, new XC_MethodHook() {
            @Override
            protected void afterHookedMethod(MethodHookParam param){
                try{
                    //Toast.makeText(snapContext, "Feed",
                     //   Toast.LENGTH_LONG).show();

                    View title = (View) getObjectField(param.thisObject, "X");
                    Context c = (Context) callMethod(param.thisObject, "getActivity");
                    //Toast.makeText(c, "Adding Button, " + c == null ? "Null" : "Not Null",
                    //        Toast.LENGTH_LONG).show();
                    ImageButton myButton = new ImageButton(c);
                    myButton.setImageDrawable(modRes.getDrawable(R.drawable.logo));
                    RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                    Toast.makeText(c, "Working",
                            Toast.LENGTH_SHORT).show();
                    RelativeLayout rl = ((RelativeLayout) title.getParent().getParent());
                    Toast.makeText(c, rl == null ? "RL Null" : "RL Not Null",
                            Toast.LENGTH_LONG).show();
                    Logger.log(rl == null ? "RL Null" : "RL Not Null");
                    rl.addView(myButton, params);
                    //setAdditionalInstanceField(param.thisObject, "myButton", myButton);
                    //logging("Snap Prefs: Removing Best-friends");
            }catch(Throwable t){
                Logger.log("Error: " + t.toString());
            }
        }});

        findAndHookMethod("com.snapchat.android.model.chat.ChatConversation", lpparam.classLoader, "e", boolean.class, XC_MethodReplacement.DO_NOTHING);
        findAndHookMethod("com.snapchat.android.model.chat.ChatConversation", lpparam.classLoader, "b", boolean.class, XC_MethodReplacement.DO_NOTHING);


        /*
        findAndHookMethod("com.snapchat.android.ui.ChatCameraButton", lpparam.classLoader, "setIsTyping", new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                param.args[0] = false;
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Logger.log(""+param.args[0]);
            }
        });
        */

        //findAndHookMethod("aty", lpparam.classLoader, "b",XC_MethodReplacement.DO_NOTHING);
        findAndHookMethod("aty", lpparam.classLoader, "a", ate, XC_MethodReplacement.DO_NOTHING);
        //findAndHookMethod("ate", lpparam.classLoader, "o", XC_MethodReplacement.DO_NOTHING);
        //findAndHookMethod("ate", lpparam.classLoader, "A", XC_MethodReplacement.DO_NOTHING);
        //findAndHookConstructor("uu", lpparam.classLoader, XC_MethodReplacement.DO_NOTHING);
        //findAndHookMethod("yy", lpparam.classLoader, "execute", XC_MethodReplacement.DO_NOTHING);
    }
}
