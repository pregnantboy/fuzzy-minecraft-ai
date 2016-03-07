//
//  RuleOutput.m
//  FuzzyAIBuilder
//
//  Created by Ian Chen on 28/1/16.
//  Copyright © 2016 Ian Chen. All rights reserved.
//

#import "RuleOutput.h"

@implementation RuleOutput

+ (NSDictionary *)actions {
    static NSDictionary *actions = nil;
    static dispatch_once_t onceToken;
    dispatch_once(&onceToken, ^{
        actions = @{
                       @"Attack Nearest Enemy": @[@"With Melee Weapon", @"With Arrows", @"With Fireballs"],
                       @"Attack Player's Target": @[@"With Melee Weapon", @"With Arrows", @"With Fireballs"],
                       @"Attack Player's Attacker": @[@"With Melee Weapon", @"With Arrows", @"With Fireballs"],
                       @"Attack Player": @[@"With Melee Weapon", @"With Arrows", @"With Fireballs"],
                       @"Run Away From": @[@"Current Target", @"Nearest Enemy", @"Attacker", @"Player's Attacker", @"Player's Target"],
                       @"Build House": @[@"Small", @"Large"],
                       @"Build Farm": @[@"Small", @"Large"],
                       @"Build Mine": @[@"Short", @"Long"],
                       @"Sow Seeds Of": @[@"Wheat", @"Pumpkin", @"Melon", @"Potato", @"Carrot"],
                       @"Harvest":@[@"Wheat", @"Pumpkin", @"Melon", @"Potato", @"Carrot"],
                       @"Mine Ores": @[@"Any"],
                       @"Seek Shelter": @[@"-"]
                    };
    });
    return actions;
}

+ (NSArray *)getPossibleActions {
    return [[[RuleOutput actions] allKeys] sortedArrayUsingSelector:@selector(localizedCaseInsensitiveCompare:)];
}

+ (NSArray *)getPossibleModifiersForAction:(NSString *)action {
    return (NSArray *)[[RuleOutput actions] valueForKey:action];
}



@end
