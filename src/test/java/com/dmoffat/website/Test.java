package com.dmoffat.website;

import name.fraser.neil.plaintext.diff_match_patch;

import java.util.LinkedList;

/**
 * @author danielmoffat
 */
public class Test {
    public static void main(String[] args) {

        String original = "Daniel Moffat";
        String revisionOne = "Daniel Moffat - is great";
        String revisionTwo = "Daniel Moffat - is great!";
        String revisionThree = "Daniel MOFFAT - is great!";
        String revisionFour = "Daniel MOFFAT - is great!!!foobar";

        String[] revisions = new String[]{original, revisionOne, revisionTwo, revisionThree, revisionFour};

        diff_match_patch diffMatchPatch = new diff_match_patch();

        LinkedList<diff_match_patch.Diff> d = diffMatchPatch.diff_main(revisions[0], revisions[1]);
        LinkedList<diff_match_patch.Diff> d2 = diffMatchPatch.diff_main(revisions[1], revisions[2]);
        LinkedList<diff_match_patch.Diff> d3 = diffMatchPatch.diff_main(revisions[2], revisions[3]);
        LinkedList<diff_match_patch.Diff> d4 = diffMatchPatch.diff_main(revisions[3], revisions[4]);

        LinkedList<diff_match_patch.Patch> p = diffMatchPatch.patch_make(d);
        LinkedList<diff_match_patch.Patch> p2 = diffMatchPatch.patch_make(d2);
        LinkedList<diff_match_patch.Patch> p3 = diffMatchPatch.patch_make(d3);
        LinkedList<diff_match_patch.Patch> p4 = diffMatchPatch.patch_make(d4);

        LinkedList<diff_match_patch.Patch> all = new LinkedList<>();
        all.addAll(p);
        all.addAll(p2);
        all.addAll(p3);
//        all.addAll(p4);

        String current = original;
        current = (String) diffMatchPatch.patch_apply(p, current)[0];
        System.out.println(current);
        current = (String) diffMatchPatch.patch_apply(p2, current)[0];
        System.out.println(current);
        current = (String) diffMatchPatch.patch_apply(p3, current)[0];
        System.out.println(current);
        current = (String) diffMatchPatch.patch_apply(p4, current)[0];
        System.out.println(current);

        System.out.println(diffMatchPatch.patch_apply(all, original)[0]);
    }
}
