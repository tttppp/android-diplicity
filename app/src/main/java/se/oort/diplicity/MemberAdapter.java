package se.oort.diplicity;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import se.oort.diplicity.apigen.Game;
import se.oort.diplicity.apigen.GameScore;
import se.oort.diplicity.apigen.Member;
import se.oort.diplicity.apigen.SingleContainer;
import se.oort.diplicity.apigen.User;
import se.oort.diplicity.apigen.UserStats;

public class MemberAdapter extends RecycleAdapter<Member, MemberAdapter.ViewHolder> {
    private View.OnClickListener delegateClickListener;
    private View.OnTouchListener delegateTouchListener;
    private RetrofitActivity retrofitActivity;
    private List<GameScore> scores;
    public class ViewHolder extends RecycleAdapter<Member, UserStatsAdapter.ViewHolder>.ViewHolder {
        public UserView userView;
        public TextView nation;
        public TextView score;
        public ViewHolder(View view) {
            super(view);
            userView = (UserView) view.findViewById(R.id.user);
            nation = (TextView) view.findViewById(R.id.nation);
            score = (TextView) view.findViewById(R.id.score);
        }
        @Override
        public void bind(Member member, int pos) {
            userView.setUser(retrofitActivity, member.User);
            if (!member.Nation.equals("")) {
                nation.setText(retrofitActivity.getResources().getString(R.string._nation_, member.Nation));
                nation.setVisibility(View.VISIBLE);
            } else {
                nation.setVisibility(View.GONE);
            }
            boolean foundScore = false;
            if (scores != null) {
                for (GameScore gameScore : scores) {
                    if (gameScore.UserId.equals(member.User.Id)) {
                        foundScore = true;
                        score.setText(retrofitActivity.getResources().getString(R.string.scs_score, gameScore.SCs, gameScore.Score.intValue()));
                    }
                }
            }
            if (foundScore) {
                score.setVisibility(View.VISIBLE);
            } else {
                score.setVisibility(View.GONE);
            }
        }
    }
    public MemberAdapter(RetrofitActivity retrofitActivity,
                         List<Member> members,
                         View.OnClickListener delegateClickListener,
                         View.OnTouchListener delegateTouchListener) {
        super(retrofitActivity, members);
        this.retrofitActivity = retrofitActivity;
        this.delegateClickListener = delegateClickListener;
        this.delegateTouchListener = delegateTouchListener;
    }
    public void setScores(List<GameScore> scores) {
        this.scores = scores;
    }
    @Override
    public MemberAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.member_list_row, parent, false);
        if (delegateClickListener != null) {
            itemView.setOnClickListener(delegateClickListener);
        }
        if (delegateTouchListener != null) {
            itemView.setOnTouchListener(delegateTouchListener);
        }
        return new ViewHolder(itemView);
    }

}
