package com.interview.recursion;

import com.google.gson.*;
import com.interview.recursion.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import static org.junit.Assert.*;

/** One source example per solution variant, with independent mutable inputs. */
@RunWith(Parameterized.class)
public class WorkbookExamplesTest {
    private static final Gson GSON=new Gson();
    private final JsonObject test;
    public WorkbookExamplesTest(String name, JsonObject test) { this.test=test; }

    @Parameterized.Parameters(name="{0}")
    public static Collection<Object[]> examples() throws IOException {
        try (Reader reader=new InputStreamReader(Objects.requireNonNull(
                WorkbookExamplesTest.class.getResourceAsStream("/examples.json")),"UTF-8")) {
            List<Object[]> result=new ArrayList<>();
            for (JsonElement item:JsonParser.parseReader(reader).getAsJsonArray()) {
                JsonObject test=item.getAsJsonObject();
                result.add(new Object[]{test.get("id").getAsString(),test});
            }
            assertEquals(200,result.size());
            return result;
        }
    }

    @Test(timeout=5000) public void sourceExample() throws Exception {
        Object module=Class.forName("com.interview.recursion.modules."+test.get("className").getAsString())
                .getDeclaredConstructor().newInstance();
        Method method=null;
        for (Method m:module.getClass().getDeclaredMethods()) {
            if (m.getName().equals(test.get("method").getAsString())) { method=m; break; }
        }
        assertNotNull(method);
        JsonArray input=test.getAsJsonArray("args");
        Object[] args=new Object[input.size()];
        for (int i=0;i<args.length;i++) {
            Class<?> type=method.getParameterTypes()[i];
            if (type==ListNode.class) args[i]=list(GSON.fromJson(input.get(i),int[].class));
            else if (type==TreeNode.class) args[i]=tree(input.get(i).getAsJsonArray());
            else args[i]=GSON.fromJson(input.get(i),method.getGenericParameterTypes()[i]);
        }
        Object result=method.invoke(module,args);
        String mode=test.get("mode").getAsString();
        JsonElement expected=test.get("expected");
        if (mode.equals("mutated")) result=args[0];
        if (result instanceof ListNode) result=listValues((ListNode)result);
        if (result instanceof TreeNode) result=treeValues((TreeNode)result);
        JsonElement actual=GSON.toJsonTree(result);
        if (mode.equals("unordered")) assertEquals(canonical(expected),canonical(actual));
        else if (mode.equals("reorganize")) {
            String original=(String)args[0], answer=(String)result;
            char[] a=original.toCharArray(),b=answer.toCharArray();
            Arrays.sort(a);Arrays.sort(b);assertArrayEquals(a,b);
            for(int i=1;i<answer.length();i++) assertNotEquals(answer.charAt(i-1),answer.charAt(i));
        } else if (mode.equals("palindrome")) {
            String answer=(String)result;
            assertEquals(expected.getAsString().length(),answer.length());
            assertTrue(((String)args[0]).contains(answer));
            assertEquals(answer,new StringBuilder(answer).reverse().toString());
        } else if (result instanceof Double) assertEquals(expected.getAsDouble(),(Double)result,1e-9);
        else assertEquals(expected,actual);
    }

    static List<String> canonical(JsonElement array) {
        List<String> result=new ArrayList<>();
        for (JsonElement item:array.getAsJsonArray()) result.add(item.toString());
        Collections.sort(result);return result;
    }
    static ListNode list(int... values) {
        ListNode dummy=new ListNode(0),tail=dummy;
        for(int value:values) { tail.next=new ListNode(value);tail=tail.next; }
        return dummy.next;
    }
    static List<Integer> listValues(ListNode head) {
        List<Integer> values=new ArrayList<>();
        while(head!=null) { assertTrue("Unexpected cycle",values.size()<10000);values.add(head.val);head=head.next; }
        return values;
    }
    static TreeNode tree(JsonArray values) {
        if(values.size()==0 || values.get(0).isJsonNull()) return null;
        TreeNode root=new TreeNode(values.get(0).getAsInt());
        Queue<TreeNode> queue=new ArrayDeque<>();queue.add(root);
        int index=1;
        while(!queue.isEmpty() && index<values.size()) {
            TreeNode node=queue.remove();
            JsonElement left=values.get(index++);
            if(!left.isJsonNull()) { node.left=new TreeNode(left.getAsInt());queue.add(node.left); }
            if(index<values.size()) {
                JsonElement right=values.get(index++);
                if(!right.isJsonNull()) { node.right=new TreeNode(right.getAsInt());queue.add(node.right); }
            }
        }
        return root;
    }
    static List<Integer> treeValues(TreeNode root) {
        List<Integer> values=new ArrayList<>();
        Queue<TreeNode> queue=new LinkedList<>();queue.add(root);
        while(!queue.isEmpty()) {
            TreeNode node=queue.remove();
            if(node==null) values.add(null);
            else { values.add(node.val);queue.add(node.left);queue.add(node.right); }
        }
        while(!values.isEmpty() && values.get(values.size()-1)==null) values.remove(values.size()-1);
        return values;
    }
}
